from flask import Flask, request, jsonify

app = Flask(__name__)


# python -m flask --app .\app.py run
@app.route('/api/get', methods=['POST'])
def get_film():
    data = request.get_json()  # Получение JSON данных из запроса
    film_name = data['filmName']
    edited_film_name = film_name + "Python"  # Пример редактирования данных
    return jsonify(edited_film_name)  # Возвращение измененных данных


if __name__ == '__main__':
    app.run(port=5000)
