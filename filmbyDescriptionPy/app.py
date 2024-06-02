from flask import Flask, request, jsonify
import requests
app = Flask(__name__)


# python -m flask --app .\app.py run
@app.route('/api/get', methods=['POST'])
def get_film():
    data = request.get_json()  # Получение JSON данных из запроса
    description = data['description']
    films = find_film(description)  # Пример редактирования данных
    return jsonify(films)  # Возвращение измененных данных


def find_film(description):
    url = f"https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=1&query={description}"
    headers = {
        "accept": "application/json",
        "X-API-KEY": "PA8MAET-82MM84J-JG1S9J2-MY5CS1M"
    }
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        data = response.json()
        film_names = [doc['name'] for doc in data.get('docs', [])]
        return film_names
    else:
        print(f"Error: Unable to fetch data, status code: {response.status_code}")
        return []


if __name__ == '__main__':
    app.run(port=5000)
