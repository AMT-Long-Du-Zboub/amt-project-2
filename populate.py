import requests, json, string, random

def gen_rand_string(prefix=None):
    """
    Generates a random string of 24 characters (alphanumeric case-sensitive)

    :param prefix: prefix to append to the random string
    :returns: random string of 24 alphanumeric characters (case-sensitive)
    """
    return (prefix + '_' if prefix != None else '') + ''.join(
        random.SystemRandom().choice(
            string.ascii_letters + string.digits
        ) for _ in range(24)
    )

LADDERS = [
    {
        "level": 0,
        "nbrPoint": 0,
        "title": "Je commence"
    },
    {
        "level": 1,
        "nbrPoint": 1000,
        "title": "Débutant"
    },
    {
        "level": 2,
        "nbrPoint": 3000,
        "title": "Débutant++"
    },
    {
        "level": 3,
        "nbrPoint": 6000,
        "title": "je connais cette erreur"
    },
    {
        "level": 4,
        "nbrPoint": 10000,
        "title": "Acolyte"
    },
    {
        "level": 5,
        "nbrPoint": 15000,
        "title": "Habitué"
    },
    {
        "level": 6,
        "nbrPoint": 21000,
        "title": "vétéran"
    },
    {
        "level": 7,
        "nbrPoint": 28000,
        "title": "expert"
    },
    {
        "level": 8,
        "nbrPoint": 36000,
        "title": "Maitre"
    },
    {
        "level": 9,
        "nbrPoint": 45000,
        "title": "Grand maitre"
    },
    {
        "level": 10,
        "nbrPoint": 55000,
        "title": "Demi-dieux"
    }
]

BADGES = [
    {
        "description": "Nouvelle arrivant sur le site",
        "name": "Welcome"
    },
    {
        "description": "C'est donc ça la démocratie",
        "name": "Voteur"
    },
    {
        "description": "Petite correction",
        "name": "Commentateur"
    },
    {
        "description": "Premiere question, bravo",
        "name": "Bronze des questions"
    },
    {
        "description": "Après 100 questions tu reçois ce badge",
        "name": "Argent des questions"
    },
    {
        "description": "Sérieux 1000 questions",
        "name": "Or des questions"
    },
    {
        "description": "Première réponse, bravo",
        "name": "Bronze des réponses"
    },
    {
        "description": "Après 100 réponses tu reçois ce badge",
        "name": "Argent des réponses"
    },
    {
        "description": "Après 1000 réponses tu reçois ce badge",
        "name": "Or des réponses"
    }
]

RULES = [
	{
	  "awardBadge": "Welcome",
	  "awardPoint": 0,
	  "type": "registration"
	},
	{
	  "awardBadge": "Voteur",
	  "awardPoint": 0,
	  "type": "vote"
	},
	{
	  "awardBadge": "Commentateur",
	  "awardPoint": 0,
	  "type": "comment"
	},
	{
	  "awardBadge": "Bronze des questions",
	  "awardPoint": 0,
	  "type": "post1Q"
	},
	{
	  "awardBadge": "Argent des questions",
	  "awardPoint": 500,
	  "type": "post100Q"
	},
	{
	  "awardBadge": "Or des questions",
	  "awardPoint": 2000,
	  "type": "post1000Q"
	},
	{
	  "awardBadge": "Bronze des réponses",
	  "awardPoint": 0,
	  "type": "post1R"
	},
	{
	  "awardBadge": "Argent des réponses",
	  "awardPoint": 500,
	  "type": "post100R"
	},
	{
	  "awardBadge": "Or des réponses",
	  "awardPoint": 2000,
	  "type": "post1000R"
	},
	{
	  "awardBadge": "",
	  "awardPoint": 100,
	  "type": "postQ"
	},
	{
	  "awardBadge": "",
	  "awardPoint": 200,
	  "type": "postR"
	}
]

REGISTRATION = 	{
    "applicationName": gen_rand_string('codemad'),
    "password": "codemad"
}

BASE_URL = 'http://127.0.0.1:8080/'
SUFFIXES = {
    'registration': 'registrations',
    'ladders': 'ladders',
    'badges': 'badges',
    'rules': 'rules',
}

API_KEY = None

req = requests.post(
    BASE_URL + SUFFIXES['registration'],
    data=json.dumps(REGISTRATION),
    headers={
        'Content-Type': 'application/json'
    }
)
if req.status_code == 200:
    API_KEY = req.json()['apiKey']
    print('App registered: {}'.format(REGISTRATION['applicationName']))
    print('API key: {}'.format(API_KEY))
else:
    print('Reg error: {}'.format(req.status_code))
    exit(1)

for ladder in LADDERS:
    req = requests.post(
        BASE_URL + SUFFIXES['ladders'],
        data=json.dumps(ladder),
        headers={
            'Content-Type': 'application/json',
            'X-API-KEY': API_KEY
        }
    )
    if req.status_code in [200, 201]:
        print('Added ladder: {}'.format(ladder['title']))
    else:
        print('Error adding ladder: {}, {}'.format(req.status_code, ladder['title']))
        exit(1)

for badge in BADGES:
    req = requests.post(
        BASE_URL + SUFFIXES['badges'],
        data=json.dumps(badge),
        headers={
            'Content-Type': 'application/json',
            'X-API-KEY': API_KEY
        }
    )
    if req.status_code in [200, 201]:
        print('Added badge: {}'.format(badge['name']))
    else:
        print('Error adding badge: {}, {}'.format(req.status_code, badge['name']))
        exit(1)

for rule in RULES:
    req = requests.post(
        BASE_URL + SUFFIXES['rules'],
        data=json.dumps(rule),
        headers={
            'Content-Type': 'application/json',
            'X-API-KEY': API_KEY
        }
    )
    if req.status_code in [200, 201]:
        print('Added rule: {}'.format(rule['type']))
    else:
        print('Error adding rule: {}, {}'.format(req.status_code, rule['type']))
        exit(1)

print('App name: {}'.format(REGISTRATION['applicationName']))
print('API key: {}'.format(API_KEY))