import sys

import Ice

import Bank
from Bank import PersonData, MoneyAmount, CurrencyIce, Date, NotSupportedCurrencyException, AccountTypeException


def get_currency_for(currency_string):
    return {
        'PLN': CurrencyIce.PLN,
        'EUR': CurrencyIce.EUR,
        'USD': CurrencyIce.USD,
        'GBP': CurrencyIce.GBP
    }[currency_string]


with Ice.initialize(sys.argv, 'config.client') as communicator:
    base = communicator.propertyToProxy('Bank.Proxy')
    bank = Bank.BankServicePrx.checkedCast(base)
    if not bank:
        raise RuntimeError("Invalid proxy")

    account = None
    password = None
    c = None
    while c != 'x':
        try:
            sys.stdout.write("==> ")
            sys.stdout.flush()
            c = sys.stdin.readline().strip()
            if c == 'create':
                print("Please provide your name, lastname, pesel:")
                data = sys.stdin.readline().strip().split(" ")
                print("Please provide your month income:")
                income = sys.stdin.readline().strip().split(" ")
                try:
                    password = bank.createAccount(PersonData(data[0], data[1], data[2]),
                                                  MoneyAmount(int(income[0]), get_currency_for(income[1])))
                    print(password)
                except NotSupportedCurrencyException as e:
                    print(e.message)
            if c == 'login':
                print('Please provide pesel and password')
                credentials = sys.stdin.readline().strip().split(" ")
                account_exists = True
                account = bank.loginStandard(credentials[0], credentials[1])
                if account is None:
                    account = bank.loginPremium(credentials[0], credentials[1])
                    if account is None:
                        account_exists = False
                        print('Account does not exists or wrong credentials were provided')
                if account_exists:
                    print("Successfully logged in")
            if c == 'logout':
                account = None
                print("Successfully logged out")
            if c == 'amount':
                if account is not None:
                    amount = account.getActualAmount()
                    print(str(amount.amount) + " " + str(amount.currency))
                    sys.stdout.flush()
                else:
                    print("You must first log in")
            if c == 'credit':
                if account is not None:
                    print("Please specify amount and currency:")
                    money = sys.stdin.readline().strip().split(" ")
                    print("Please specify end date: (month year)")
                    date = sys.stdin.readline().strip().split(" ")

                    amount = int(money[0])
                    currency = get_currency_for(money[1])
                    endDate = Date(10, int(date[0]), int(date[1]))

                    try:
                        creditCost = account.getCreditCost(MoneyAmount(amount, currency), endDate)
                        print('Credit cost:')
                        print(str(creditCost.actualCurrencyMoney))
                        print(str(creditCost.nativeCurrencyMoney))
                    except (NotSupportedCurrencyException, AccountTypeException) as e:
                        print(e.message)
                else:
                    print("You must first log in")
        except Ice.Exception as ex:
            print(ex)
