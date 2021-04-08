from flask import Flask, render_template, Response, jsonify, request
from camera import VideoCamera
from flask_mysqldb import MySQL
# 설치! : pip install Flask-MySQLdb


app = Flask(__name__)
app.config['MYSQL_HOST'] = '127.0.0.1'
app.config['MYSQL_USER'] = 'root'
app.config['MYSQL_PASSWORD'] = '0000'
app.config['MYSQL_DB']='security'
#app.config['MYSQL_DATABASE']='security'
#app.config['MYSQL_PORT']=3306

mysql = MySQL(app)

video_camera = None
global_frame = None

@app.route('/')
def index():
    #data2 = video_camera.gettimelist()
    #cur = mysql.connection.cursor()
    #cur.execute("SELECT * FROM test")
    #fetchdata = cur.fetchall()
    #cur.close()
    #aaa = VideoCamera().get_frame().total
    #total
    #return render_template('index.html', data = aaa)
    #return render_template('index.html', data = fetchdata)
    #print("server.py!!!!!!!!!!!!!!!!!!!!!!!!",data2)
    return render_template('index.html')

@app.route('/record_status', methods=['POST'])
def record_status():
    global video_camera 
    if video_camera == None:
        video_camera = VideoCamera()

    json = request.get_json()

    status = json['status']

    if status == "true":
        video_camera.start_record()
        return jsonify(result="started")
    else:
        video_camera.stop_record()
        return jsonify(result="stopped")

def video_stream():
    global video_camera 
    global global_frame

    if video_camera == None:
        video_camera = VideoCamera()
        
    while True:
        frame = video_camera.get_frame()

        if frame != None:
            global_frame = frame
            yield (b'--frame\r\n'
                    b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n\r\n')
        else:
            yield (b'--frame\r\n'
                            b'Content-Type: image/jpeg\r\n\r\n' + global_frame + b'\r\n\r\n')

@app.route('/video_viewer')
def video_viewer():
    return Response(video_stream(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
    app.run(debug=True, threaded=True)