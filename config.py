from configparser import ConfigParser

def Config(filename = "database.ini", section = "posgresql"):
    parser = ConfigParser()

    parser.read(filename)

    db = {}
    if parser.has_section(section):
        params = parser.items(section)
        for param in params:
            db[param[0]] = param[1]
    
    else:
        raise Exception(f"Section {section} in not found in the {filename} file")

    print(db)

Config()