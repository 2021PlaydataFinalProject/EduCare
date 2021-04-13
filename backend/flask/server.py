from flask import Flask, render_template, Response, jsonify, request
from camera import VideoCamera
from flask_mysqldb import MySQL
# 설치! : pip install Flask-MySQLdb
import time

app = Flask(__name__)
# database에 접근
app.config['MYSQL_HOST'] = '127.0.0.1'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = '0000'
app.config['MYSQL_DB']='educare'
#app.config['MYSQL_DATABASE']='security'
#app.config['MYSQL_PORT']=3306
mysql = MySQL(app)
video_camera = None
global_frame = None
status = 'true'

@app.route('/')
def index():
    #data2 = video_camera.gettimelist()
    # database를 사용하기 위한 cursor를 세팅합니다.
    cur = mysql.connection.cursor()
    # 쿼리문 실행
    cur.execute("SELECT * FROM testproblem")
    fetchdata = cur.fetchall()
    # print(fetchdata[0][4])
    prosel_list = []
    # 시험 문제1
    prosel_list.append(fetchdata[0][2])
    # 시험 선지1
    prosel_list.append(fetchdata[0][5].split(','))
    # [string(시험문제), [1.assdaf, 2.fasdfasd, 3.asdfasdf 4.asdfasd]]
    # print('prosel_list size : ',len(prosel_list))
    # 시험 문제2
    prosel_list.append(fetchdata[1][2])
    # 시험 선지2
    prosel_list.append(fetchdata[1][5].split(','))
    #cur.close()
    #aaa = VideoCamera().get_frame().total
    #total
    #return render_template('index.html', data = aaa)
    # print('testproblem_list : ',testproblem_list)
    return render_template('index.html', data_list = prosel_list)
    #print("server.py!!!!!!!!!!!!!!!!!!!!!!!!",data2)
    # timelist = ["Hey", "How", "Are", "You"]
    # return render_template('index.html',recordtime=timelist)

@app.route('/record_status', methods=['POST'])
def record_status():
    global video_camera 
    if video_camera == None:
        video_camera = VideoCamera()
    json = request.get_json()
    global status 
    status = json['status']
    print("record_status==============",status)
    if status == "true":
        video_camera.start_record()
        return jsonify(result="started")
    if status == "false":
        video_camera.stop_record()
        return jsonify(result="stopped")

def video_stream():
    global video_camera 
    global global_frame
    start_time = time.time()
    time_list = list()

    if video_camera == None:
        video_camera = VideoCamera(start_time,time_list)
    
  
    while True:
        frame = video_camera.get_frame()
        if frame != None:
            global_frame = frame
            yield (b'--frame\r\n'
                    b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
        else:
            yield (b'--frame\r\n'
                            b'Content-Type: image/jpeg\r\n\r\n' + global_frame + b'\r\n\r\n')
        print("in sever.py",time_list)
    

@app.route('/video_viewer')
def video_viewer():
    return Response(video_stream(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')
if __name__ == '__main__':
    app.run(debug=True, threaded=True)