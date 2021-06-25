from libr import Person

def main():
    name = str(input("Enter name: "))
    someone = Person(name)
    print(someone)

if __name__ == "__main__":
    main()