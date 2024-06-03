from flask import Flask, request, jsonify
import requests

app = Flask(__name__)


# python -m flask --app .\app.py run
@app.route('/name/get', methods=['POST'])
def get_film():
    data = request.get_json()  # Получение JSON данных из запроса
    description = data['description']
    films = find_film(description)  # Пример редактирования данных
    return jsonify(films)  # Возвращение измененных данных


@app.route('/info/get', methods=['POST'])
def get_info():
    data = request.get_json()  # Получение JSON данных из запроса
    name = data['name']
    info = get_film_info(name)  # Пример редактирования данных
    return jsonify(info)  # Возвращение измененных данных


def get_film_info(name):
    url = f"https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=1&query={name}"
    headers = {
        "accept": "application/json",
        "X-API-KEY": "PA8MAET-82MM84J-JG1S9J2-MY5CS1M"
    }
    response = requests.get(url, headers=headers)
    if response.status_code == 200:
        data = response.json()
        json = data.get('docs', [])
        first_doc = json[0]
        info = [
            first_doc.get("name"),
            first_doc.get("description") or "описание неизвестно",
            first_doc.get("movieLength") or "длина фильма неизвестна",
            first_doc.get("ageRating") or "возрастной рейтинг неизвестен"
        ]
        return info
    else:
        print(f"Error: Unable to fetch data, status code: {response.status_code}")
        return []


# Потом к этому методу подключим мл модель, а запрос из кинопоиска удалим
def find_film(description):
    url = f"https://api.kinopoisk.dev/v1.4/movie/search?page=1&limit=2&query={description}"
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
