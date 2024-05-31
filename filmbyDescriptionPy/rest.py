from flask import Flask

rest = Flask(__name__)


@rest.route("/")
def home():
    return "Hello world!"
