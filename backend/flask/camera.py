import os
import cv2
import threading
from flask import Flask, render_template, Response
import tensorflow as tf
import numpy as np
from yolo_helper import YoloV3, load_darknet_weights, draw_outputs
import time
import sys
import pymysql

yolo = YoloV3()
load_darknet_weights(yolo, 'yolov3.weights')

#start_time = time.time()
timelist = list()
import time

# class STOPWATCH(object):
#     def time_convert(sec):
#         mins = sec // 60 
#         sec = sec % 60
#         hours = mins // 60
#         mins = mins % 60
#         print("Time lapsed = {0}:{1}:{2}".format(int(hours),int(mins),sec))
#     try:
#         hours = 0
#         while True:
#             for minutes in range(0,60):
#                 for seconds in range(0,60):
#                     time.sleep(1)
#                     print(hours,":",minutes,":",seconds+1)



class RecordingThread (threading.Thread):
    def __init__(self, name, camera):
        threading.Thread.__init__(self)
        self.name = name
        self.isRunning = True
        self.cap = camera #<VideoCapture 000001C4247745B0>
        #print('camera', camera)
        fourcc = cv2.VideoWriter_fourcc(*'MJPG')
        self.out = cv2.VideoWriter('./static/video.avi',fourcc, 20.0, (640,480))
    def run(self):
        while self.isRunning:
            ret, frame = self.cap.read()
            if ret:
                self.out.write(frame)
        self.out.release()
    def stop(self):
        self.isRunning = False
    def __del__(self):
        self.out.release()
class VideoCamera(object):
    def __init__(self):
        # Open a camera
        self.cap = cv2.VideoCapture(0)
        # print('영상시작',time.strftime("%H:%M:%S", time.gmtime(time.time() - start_time)))
        # Initialize video recording environment
        self.is_record = False
        self.out = None
        # Thread for recording
        self.recordingThread = None
#        if self.is_record:
#            self.start_time = time.time()
    def __del__(self):
        self.cap.release()

 
    def get_frame(self):
        #timelist = list()
        while(True):
            ret, frame = self.cap.read()
            if ret == False:
                break
            img = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)
            img = cv2.resize(img, (320, 320))
            img = img.astype(np.float32)
            img = np.expand_dims(img, 0)
            img = img / 255
            class_names = [c.strip() for c in open("models/classes.TXT").readlines()]
            boxes, scores, classes, nums = yolo(img)
            count=0
            #mysql 연동
            #conn = pymysql.connect(host='localhost', user='root', password='0000',
            #                    db='security', charset='utf8')
            #curs = conn.cursor()
            #timelist = list()
            if self.is_record:
                elapsed_time = time.time() - start_time
            for i in range(nums[0]):
                temp = classes[0][i]
                if int(classes[0][i] == 0):
                    count +=1
                if self.is_record and int(temp==62 or temp == 63 or temp==67 or temp==73): #tvmonitor, laptop, cell phone, book
                    timelist.append(time.strftime("%H:%M:%S", time.gmtime(elapsed_time)))
                    #print('test용', cheat_time) # 녹화중일때만  --> if써서!
                    #print('doubt',timelist)
                    #print('Mobile Phone detected')
                    # return True -> mySQL
                    # string 값으로 영상 시작한 지 얼마나 지났는 지 확인
                    #elapsed_time = time.time() - start_time 
                    #print(time.strftime("%H:%M:%S", time.gmtime(elapsed_time))) 
                    #print(type(time.strftime("%H:%M:%S", time.gmtime(elapsed_time))))
            if self.is_record and count != 1:
                #print('No person detected')
                # return True -> mySQL
                timelist.append(time.strftime("%H:%M:%S", time.gmtime(elapsed_time)))
                #print('noperson',timelist)
            # elif count > 1:  #print('More than one person detected')
            #     timelist.append(time.strftime("%H:%M:%S", time.gmtime(elapsed_time)))
                #print('manypeople',timelist) 
            #curs.execute('''INSERT INTO studenttest (is_cheating) values (%s)''',(timelist))
            #conn.commit()
            #conn.close()
            print('total',timelist) #db에 보내야하는 값
            frame = draw_outputs(frame, (boxes, scores, classes, nums), class_names)
            # cv2.imshow('Prediction', image)
            # if cv2.waitKey(1) & 0xFF == ord('q'):
            #     break
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            #yield (b'--frame\r\n'
            #         b'Content-Type: frame/jpg\r\n\r\n' + frame + b'\r\n')  # concat frame one by one and show result
            # Record video
            if self.is_record:
                if self.out == None:
                    fourcc = cv2.VideoWriter_fourcc(*'MJPG')
                    self.out = cv2.VideoWriter('./static/video.avi',fourcc, 20.0, (640,480))
                ret, frame = self.cap.read()
                if ret:
                    self.out.write(frame)
            else:
                if self.out != None:
                    self.out.release()
                    self.out = None   
            return buffer.tobytes()
        else:
            return None

    def start_record(self):
        self.is_record = True
        self.recordingThread = RecordingThread("Video Recording Thread", self.cap)
        self.recordingThread.start()
        global start_time
        start_time = time.time()
        
        
        
        # global cheat_time
        # cheat_time = time.time() - start_time
        #              #global cheat_time == correct_elapsed_time
        # print('녹화시작:',time.strftime("%H:%M:%S", time.gmtime(cheat_time)))



    def stop_record(self):
        self.is_record = False
        if self.recordingThread != None:
            self.recordingThread.stop()
            # running = Flase

 
    # seconds = 0
    # minutes = 0
    # def start_record(self):
    #     self.is_record = True
    #     self.recordingThread = RecordingThread("Video Recording Thread", self.cap)
    #     self.recordingThread.start()
    #     correct_elapsed_time = time.time() - start_time
    #     print('녹화시작:',time.strftime("%H:%M:%S", time.gmtime(correct_elapsed_time)))



        # fps = vidcapture.get(cv2.CAP_PROP_FPS)
        # totalNoFrames = vidcapture.get(cv2.CAP_PROP_FRAME_COUNT);
        # durationInSeconds = float(totalNoFrames) / float(fps)
        # seconds = 0
        # minutes = 0
        
        # while running:
        #     try:
        #         sys.stdout.write("\r{minutes} Minutes {seconds} Seconds".format(minutes=minutes, seconds=seconds))
        #         sys.stdout.flush()
        #         time.sleep(1)
        #         seconds = int(time.time() - time_start) - minutes * 60
        #         if seconds >= 60:
        #             minutes += 1
        #             seconds = 0


    # def stop_record(self):
    #     self.is_record = False
    #     if self.recordingThread != None:
    #         self.recordingThread.stop()
    #         # running = Flase



        #start_time = time.time()
        

        #print('영상시작시간', self.respond_time)
    # def stop_record(self):
    #     self.is_record = False
    #     if self.recordingThread != None:
    #         self.recordingThread.stop()
    #         end_time = time.time()
    #         time_lapsed = end_time - start_time
    #         result = time_convert(time_lapsed)
    #         print('시험 총 걸린 시간:', result)

    # def start_record(self):
    #     self.is_record = True
    #     self.recordingThread = RecordingThread("Video Recording Thread", self.cap)
    #     self.recordingThread.start()
