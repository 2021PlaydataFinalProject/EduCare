from flask import Flask, render_template, Response, jsonify, request
from camera import VideoCamera,RecordingThread
from flask_mysqldb import MySQL
# 설치! : pip install Flask-MySQLdb
import time

# No 'Access-Control-Allow-Origin' 해결하기 위함
from flask_cors import CORS

app = Flask(__name__)

# No 'Access-Control-Allow-Origin' 해결하기 위함
CORS(app, resources={r'*': {'origins': '*'}})

# database에 접근
app.config['MYSQL_HOST'] = '127.0.0.1'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = '0000'
app.config['MYSQL_DB']='educare'

mysql = MySQL(app)
video_camera = None
global_frame = None
status = 'true'
time_list = None

# @app.route('/')
@app.route('/<username>/<testnum>')
def index(username, testnum):    
    # print(username, testnum)
    # 쿼리문 실행
    cur = mysql.connection.cursor()
    cur.execute("SELECT * FROM testproblem where test_num = %s", testnum)

    fetchdata = cur.fetchall()
    print(len(fetchdata))
    prosel_list = []
    for problem in fetchdata:
        prosel_list.append(problem[4]) # 시험 문제 번호 
        prosel_list.append(problem[2]) #시험 문제
        prosel_list.append(problem[5].split(',')) # 시험선지

    return render_template('index.html', data_list = prosel_list)

@app.route('/record_status', methods=['POST'])
def record_status():
    global video_camera 
    global time_list

    if video_camera != None:
        json = request.get_json()
        global status 
        status = json['status']
        print("record_status==============",status) # 녹화정지 버튼 클릭시 false return, 이외 상황은 고려X    
        # if status == "true":
        #     video_camera.start_record()
        #     return jsonify(result="started")
        if status == "false":
            video_camera.stop_record()
            # db에 모든 정보 저장(answerlist, time_list)
            if time_list != None:
                print("when video finished",time_list)
                
            return jsonify(result="stopped")

def video_stream():
    global video_camera 
    global global_frame
    start_time = time.time()
    global time_list
    time_list = list()

    if video_camera == None:
        video_camera = VideoCamera(start_time,time_list)
    
    video_camera.start_record()

    while True:
        frame = video_camera.get_frame()
        if frame != None:
            global_frame = frame
            yield (b'--frame\r\n'
                    b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
        else:
            yield (b'--frame\r\n'
                            b'Content-Type: image/jpeg\r\n\r\n' + global_frame + b'\r\n\r\n')
        #print("in sever.py",time_list)
    

@app.route('/video_viewer')
def video_viewer():
    return Response(video_stream(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')
if __name__ == '__main__':
    app.run(debug=True, threaded=True)