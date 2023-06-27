import random
from random import randint

from faker import Faker

fake = Faker()

index = 1

allIDs = []

with open('populateFanTable.sql', 'w') as f:
    generated_name_set = set()
    for i in range(1000):
        if (i % 100 == 0):
            print(f'Generated {i * 1000} records')

        values = []
        for j in range(1000):

            ID = index
            index += 1

            age = random.randint(20, 60)

            name = fake.name()[:10]
            if name in generated_name_set:
                name += f"- {i * 10000 + j} new user"
            else:
                generated_name_set.add(name)

            name = name.replace("'", "''")
            nationality = fake.country()
            nationality = nationality.replace("'", "''")
            occupation = fake.job()
            occupation = occupation.replace("'", "''")

            user = ID % 2 + 1

            values.append(
                f'({ID}, {age}, \'{name}\', \'{nationality}\', \'{occupation}\', \'{nationality}\', {user})'
            )

        print(
            f'INSERT INTO esport2.fan_tbl (fid, age, name, nationality, occupation, place_of_birth, id) VALUES {", ".join(values)};',
            file=f)