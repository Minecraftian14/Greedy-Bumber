from flask import Flask, jsonify, request
from flask_sqlalchemy import SQLAlchemy
import datetime

app = Flask(__name__)
app.debug=True
app.config['SQLALCHEMY_DATABASE_URI'] = "sqlite:///data.sql"
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False
db = SQLAlchemy(app)

class Guild(db.Model):
    serial = db.Column(db.Integer,primary_key=True)
    id = db.Column(db.Integer)
    name = db.Column(db.String)
    last_bump = db.Column(db.Date)
    joined = db.Column(db.Date)

@app.errorhandler(404)
def err_404(e):
    return "Sorry your request return 404 error!"
    
@app.route("/server/<id>")
def get_guild(id):
    guild = Guild.query.filter_by(id=id).first()
    if guild:
        return guild.name
    return "not found"


@app.route("/bump",methods=["POST"])
def bumping_sys():
    id = int(request.args.get('id'))
    guild = Guild.query.filter_by(id=id).first()
    if guild:
        if guild.last_bump < (datetime.datetime.utcnow()-datetime.timedelta(seconds=10)):
            guild.last_bump = datetime.datetime.utcnow()
            db.session.commit()
            return jsonify({"status":200}),200
        else:
            return jsonify({"status":403}),403
    else:
       return jsonify({"status":404}),404
       
if __name__=='__main__':
    app.run(host='0.0.0.0')