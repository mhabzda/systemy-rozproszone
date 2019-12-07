# -*- coding: utf-8 -*-
# **********************************************************************
#
# Copyright (c) 2003-2018 ZeroC, Inc. All rights reserved.
#
# This copy of Ice is licensed to you under the terms described in the
# ICE_LICENSE file included in this distribution.
#
# **********************************************************************
#
# Ice version 3.7.1
#
# <auto-generated>
#
# Generated from file `bank.ice'
#
# Warning: do not edit this file.
#
# </auto-generated>
#

from sys import version_info as _version_info_
import Ice, IcePy

# Start of module Bank
_M_Bank = Ice.openModule('Bank')
__name__ = 'Bank'

if 'CurrencyIce' not in _M_Bank.__dict__:
    _M_Bank.CurrencyIce = Ice.createTempClass()
    class CurrencyIce(Ice.EnumBase):

        def __init__(self, _n, _v):
            Ice.EnumBase.__init__(self, _n, _v)

        def valueOf(self, _n):
            if _n in self._enumerators:
                return self._enumerators[_n]
            return None
        valueOf = classmethod(valueOf)

    CurrencyIce.PLN = CurrencyIce("PLN", 0)
    CurrencyIce.EUR = CurrencyIce("EUR", 1)
    CurrencyIce.USD = CurrencyIce("USD", 2)
    CurrencyIce.GBP = CurrencyIce("GBP", 3)
    CurrencyIce._enumerators = { 0:CurrencyIce.PLN, 1:CurrencyIce.EUR, 2:CurrencyIce.USD, 3:CurrencyIce.GBP }

    _M_Bank._t_CurrencyIce = IcePy.defineEnum('::Bank::CurrencyIce', CurrencyIce, (), CurrencyIce._enumerators)

    _M_Bank.CurrencyIce = CurrencyIce
    del CurrencyIce

if 'MoneyAmount' not in _M_Bank.__dict__:
    _M_Bank.MoneyAmount = Ice.createTempClass()
    class MoneyAmount(object):
        def __init__(self, amount=0, currency=_M_Bank.CurrencyIce.PLN):
            self.amount = amount
            self.currency = currency

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.amount)
            _h = 5 * _h + Ice.getHash(self.currency)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_Bank.MoneyAmount):
                return NotImplemented
            else:
                if self.amount is None or other.amount is None:
                    if self.amount != other.amount:
                        return (-1 if self.amount is None else 1)
                else:
                    if self.amount < other.amount:
                        return -1
                    elif self.amount > other.amount:
                        return 1
                if self.currency is None or other.currency is None:
                    if self.currency != other.currency:
                        return (-1 if self.currency is None else 1)
                else:
                    if self.currency < other.currency:
                        return -1
                    elif self.currency > other.currency:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_MoneyAmount)

        __repr__ = __str__

    _M_Bank._t_MoneyAmount = IcePy.defineStruct('::Bank::MoneyAmount', MoneyAmount, (), (
        ('amount', (), IcePy._t_long),
        ('currency', (), _M_Bank._t_CurrencyIce)
    ))

    _M_Bank.MoneyAmount = MoneyAmount
    del MoneyAmount

if 'Date' not in _M_Bank.__dict__:
    _M_Bank.Date = Ice.createTempClass()
    class Date(object):
        def __init__(self, day=0, month=0, year=0):
            self.day = day
            self.month = month
            self.year = year

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.day)
            _h = 5 * _h + Ice.getHash(self.month)
            _h = 5 * _h + Ice.getHash(self.year)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_Bank.Date):
                return NotImplemented
            else:
                if self.day is None or other.day is None:
                    if self.day != other.day:
                        return (-1 if self.day is None else 1)
                else:
                    if self.day < other.day:
                        return -1
                    elif self.day > other.day:
                        return 1
                if self.month is None or other.month is None:
                    if self.month != other.month:
                        return (-1 if self.month is None else 1)
                else:
                    if self.month < other.month:
                        return -1
                    elif self.month > other.month:
                        return 1
                if self.year is None or other.year is None:
                    if self.year != other.year:
                        return (-1 if self.year is None else 1)
                else:
                    if self.year < other.year:
                        return -1
                    elif self.year > other.year:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_Date)

        __repr__ = __str__

    _M_Bank._t_Date = IcePy.defineStruct('::Bank::Date', Date, (), (
        ('day', (), IcePy._t_short),
        ('month', (), IcePy._t_short),
        ('year', (), IcePy._t_int)
    ))

    _M_Bank.Date = Date
    del Date

if 'PersonData' not in _M_Bank.__dict__:
    _M_Bank.PersonData = Ice.createTempClass()
    class PersonData(object):
        def __init__(self, name='', lastName='', pesel=''):
            self.name = name
            self.lastName = lastName
            self.pesel = pesel

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.name)
            _h = 5 * _h + Ice.getHash(self.lastName)
            _h = 5 * _h + Ice.getHash(self.pesel)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_Bank.PersonData):
                return NotImplemented
            else:
                if self.name is None or other.name is None:
                    if self.name != other.name:
                        return (-1 if self.name is None else 1)
                else:
                    if self.name < other.name:
                        return -1
                    elif self.name > other.name:
                        return 1
                if self.lastName is None or other.lastName is None:
                    if self.lastName != other.lastName:
                        return (-1 if self.lastName is None else 1)
                else:
                    if self.lastName < other.lastName:
                        return -1
                    elif self.lastName > other.lastName:
                        return 1
                if self.pesel is None or other.pesel is None:
                    if self.pesel != other.pesel:
                        return (-1 if self.pesel is None else 1)
                else:
                    if self.pesel < other.pesel:
                        return -1
                    elif self.pesel > other.pesel:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_PersonData)

        __repr__ = __str__

    _M_Bank._t_PersonData = IcePy.defineStruct('::Bank::PersonData', PersonData, (), (
        ('name', (), IcePy._t_string),
        ('lastName', (), IcePy._t_string),
        ('pesel', (), IcePy._t_string)
    ))

    _M_Bank.PersonData = PersonData
    del PersonData

if 'CreditCostPair' not in _M_Bank.__dict__:
    _M_Bank.CreditCostPair = Ice.createTempClass()
    class CreditCostPair(object):
        def __init__(self, nativeCurrencyMoney=Ice._struct_marker, actualCurrencyMoney=Ice._struct_marker):
            if nativeCurrencyMoney is Ice._struct_marker:
                self.nativeCurrencyMoney = _M_Bank.MoneyAmount()
            else:
                self.nativeCurrencyMoney = nativeCurrencyMoney
            if actualCurrencyMoney is Ice._struct_marker:
                self.actualCurrencyMoney = _M_Bank.MoneyAmount()
            else:
                self.actualCurrencyMoney = actualCurrencyMoney

        def __hash__(self):
            _h = 0
            _h = 5 * _h + Ice.getHash(self.nativeCurrencyMoney)
            _h = 5 * _h + Ice.getHash(self.actualCurrencyMoney)
            return _h % 0x7fffffff

        def __compare(self, other):
            if other is None:
                return 1
            elif not isinstance(other, _M_Bank.CreditCostPair):
                return NotImplemented
            else:
                if self.nativeCurrencyMoney is None or other.nativeCurrencyMoney is None:
                    if self.nativeCurrencyMoney != other.nativeCurrencyMoney:
                        return (-1 if self.nativeCurrencyMoney is None else 1)
                else:
                    if self.nativeCurrencyMoney < other.nativeCurrencyMoney:
                        return -1
                    elif self.nativeCurrencyMoney > other.nativeCurrencyMoney:
                        return 1
                if self.actualCurrencyMoney is None or other.actualCurrencyMoney is None:
                    if self.actualCurrencyMoney != other.actualCurrencyMoney:
                        return (-1 if self.actualCurrencyMoney is None else 1)
                else:
                    if self.actualCurrencyMoney < other.actualCurrencyMoney:
                        return -1
                    elif self.actualCurrencyMoney > other.actualCurrencyMoney:
                        return 1
                return 0

        def __lt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r < 0

        def __le__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r <= 0

        def __gt__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r > 0

        def __ge__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r >= 0

        def __eq__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r == 0

        def __ne__(self, other):
            r = self.__compare(other)
            if r is NotImplemented:
                return r
            else:
                return r != 0

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_CreditCostPair)

        __repr__ = __str__

    _M_Bank._t_CreditCostPair = IcePy.defineStruct('::Bank::CreditCostPair', CreditCostPair, (), (
        ('nativeCurrencyMoney', (), _M_Bank._t_MoneyAmount),
        ('actualCurrencyMoney', (), _M_Bank._t_MoneyAmount)
    ))

    _M_Bank.CreditCostPair = CreditCostPair
    del CreditCostPair

if 'NotSupportedCurrencyException' not in _M_Bank.__dict__:
    _M_Bank.NotSupportedCurrencyException = Ice.createTempClass()
    class NotSupportedCurrencyException(Ice.UserException):
        def __init__(self, message="Not supported currency was used"):
            self.message = message

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::Bank::NotSupportedCurrencyException'

    _M_Bank._t_NotSupportedCurrencyException = IcePy.defineException('::Bank::NotSupportedCurrencyException', NotSupportedCurrencyException, (), False, None, (('message', (), IcePy._t_string, False, 0),))
    NotSupportedCurrencyException._ice_type = _M_Bank._t_NotSupportedCurrencyException

    _M_Bank.NotSupportedCurrencyException = NotSupportedCurrencyException
    del NotSupportedCurrencyException

if 'AccountTypeException' not in _M_Bank.__dict__:
    _M_Bank.AccountTypeException = Ice.createTempClass()
    class AccountTypeException(Ice.UserException):
        def __init__(self, message="User with standard account cannot ask for credit"):
            self.message = message

        def __str__(self):
            return IcePy.stringifyException(self)

        __repr__ = __str__

        _ice_id = '::Bank::AccountTypeException'

    _M_Bank._t_AccountTypeException = IcePy.defineException('::Bank::AccountTypeException', AccountTypeException, (), False, None, (('message', (), IcePy._t_string, False, 0),))
    AccountTypeException._ice_type = _M_Bank._t_AccountTypeException

    _M_Bank.AccountTypeException = AccountTypeException
    del AccountTypeException

_M_Bank._t_Account = IcePy.defineValue('::Bank::Account', Ice.Value, -1, (), False, True, None, ())

if 'AccountPrx' not in _M_Bank.__dict__:
    _M_Bank.AccountPrx = Ice.createTempClass()
    class AccountPrx(Ice.ObjectPrx):

        def getCreditCost(self, amount, finishDate, context=None):
            return _M_Bank.Account._op_getCreditCost.invoke(self, ((amount, finishDate), context))

        def getCreditCostAsync(self, amount, finishDate, context=None):
            return _M_Bank.Account._op_getCreditCost.invokeAsync(self, ((amount, finishDate), context))

        def begin_getCreditCost(self, amount, finishDate, _response=None, _ex=None, _sent=None, context=None):
            return _M_Bank.Account._op_getCreditCost.begin(self, ((amount, finishDate), _response, _ex, _sent, context))

        def end_getCreditCost(self, _r):
            return _M_Bank.Account._op_getCreditCost.end(self, _r)

        def getActualAmount(self, context=None):
            return _M_Bank.Account._op_getActualAmount.invoke(self, ((), context))

        def getActualAmountAsync(self, context=None):
            return _M_Bank.Account._op_getActualAmount.invokeAsync(self, ((), context))

        def begin_getActualAmount(self, _response=None, _ex=None, _sent=None, context=None):
            return _M_Bank.Account._op_getActualAmount.begin(self, ((), _response, _ex, _sent, context))

        def end_getActualAmount(self, _r):
            return _M_Bank.Account._op_getActualAmount.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_Bank.AccountPrx.ice_checkedCast(proxy, '::Bank::Account', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_Bank.AccountPrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::Bank::Account'
    _M_Bank._t_AccountPrx = IcePy.defineProxy('::Bank::Account', AccountPrx)

    _M_Bank.AccountPrx = AccountPrx
    del AccountPrx

    _M_Bank.Account = Ice.createTempClass()
    class Account(Ice.Object):

        def ice_ids(self, current=None):
            return ('::Bank::Account', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Bank::Account'

        @staticmethod
        def ice_staticId():
            return '::Bank::Account'

        def getCreditCost(self, amount, finishDate, current=None):
            raise NotImplementedError("servant method 'getCreditCost' not implemented")

        def getActualAmount(self, current=None):
            raise NotImplementedError("servant method 'getActualAmount' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_AccountDisp)

        __repr__ = __str__

    _M_Bank._t_AccountDisp = IcePy.defineClass('::Bank::Account', Account, (), None, ())
    Account._ice_type = _M_Bank._t_AccountDisp

    Account._op_getCreditCost = IcePy.Operation('getCreditCost', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Bank._t_MoneyAmount, False, 0), ((), _M_Bank._t_Date, False, 0)), (), ((), _M_Bank._t_CreditCostPair, False, 0), (_M_Bank._t_NotSupportedCurrencyException, _M_Bank._t_AccountTypeException))
    Account._op_getActualAmount = IcePy.Operation('getActualAmount', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (), (), ((), _M_Bank._t_MoneyAmount, False, 0), ())

    _M_Bank.Account = Account
    del Account

_M_Bank._t_BankService = IcePy.defineValue('::Bank::BankService', Ice.Value, -1, (), False, True, None, ())

if 'BankServicePrx' not in _M_Bank.__dict__:
    _M_Bank.BankServicePrx = Ice.createTempClass()
    class BankServicePrx(Ice.ObjectPrx):

        def createAccount(self, personData, monthIncome, context=None):
            return _M_Bank.BankService._op_createAccount.invoke(self, ((personData, monthIncome), context))

        def createAccountAsync(self, personData, monthIncome, context=None):
            return _M_Bank.BankService._op_createAccount.invokeAsync(self, ((personData, monthIncome), context))

        def begin_createAccount(self, personData, monthIncome, _response=None, _ex=None, _sent=None, context=None):
            return _M_Bank.BankService._op_createAccount.begin(self, ((personData, monthIncome), _response, _ex, _sent, context))

        def end_createAccount(self, _r):
            return _M_Bank.BankService._op_createAccount.end(self, _r)

        def loginStandard(self, pesel, password, context=None):
            return _M_Bank.BankService._op_loginStandard.invoke(self, ((pesel, password), context))

        def loginStandardAsync(self, pesel, password, context=None):
            return _M_Bank.BankService._op_loginStandard.invokeAsync(self, ((pesel, password), context))

        def begin_loginStandard(self, pesel, password, _response=None, _ex=None, _sent=None, context=None):
            return _M_Bank.BankService._op_loginStandard.begin(self, ((pesel, password), _response, _ex, _sent, context))

        def end_loginStandard(self, _r):
            return _M_Bank.BankService._op_loginStandard.end(self, _r)

        def loginPremium(self, pesel, password, context=None):
            return _M_Bank.BankService._op_loginPremium.invoke(self, ((pesel, password), context))

        def loginPremiumAsync(self, pesel, password, context=None):
            return _M_Bank.BankService._op_loginPremium.invokeAsync(self, ((pesel, password), context))

        def begin_loginPremium(self, pesel, password, _response=None, _ex=None, _sent=None, context=None):
            return _M_Bank.BankService._op_loginPremium.begin(self, ((pesel, password), _response, _ex, _sent, context))

        def end_loginPremium(self, _r):
            return _M_Bank.BankService._op_loginPremium.end(self, _r)

        @staticmethod
        def checkedCast(proxy, facetOrContext=None, context=None):
            return _M_Bank.BankServicePrx.ice_checkedCast(proxy, '::Bank::BankService', facetOrContext, context)

        @staticmethod
        def uncheckedCast(proxy, facet=None):
            return _M_Bank.BankServicePrx.ice_uncheckedCast(proxy, facet)

        @staticmethod
        def ice_staticId():
            return '::Bank::BankService'
    _M_Bank._t_BankServicePrx = IcePy.defineProxy('::Bank::BankService', BankServicePrx)

    _M_Bank.BankServicePrx = BankServicePrx
    del BankServicePrx

    _M_Bank.BankService = Ice.createTempClass()
    class BankService(Ice.Object):

        def ice_ids(self, current=None):
            return ('::Bank::BankService', '::Ice::Object')

        def ice_id(self, current=None):
            return '::Bank::BankService'

        @staticmethod
        def ice_staticId():
            return '::Bank::BankService'

        def createAccount(self, personData, monthIncome, current=None):
            raise NotImplementedError("servant method 'createAccount' not implemented")

        def loginStandard(self, pesel, password, current=None):
            raise NotImplementedError("servant method 'loginStandard' not implemented")

        def loginPremium(self, pesel, password, current=None):
            raise NotImplementedError("servant method 'loginPremium' not implemented")

        def __str__(self):
            return IcePy.stringify(self, _M_Bank._t_BankServiceDisp)

        __repr__ = __str__

    _M_Bank._t_BankServiceDisp = IcePy.defineClass('::Bank::BankService', BankService, (), None, ())
    BankService._ice_type = _M_Bank._t_BankServiceDisp

    BankService._op_createAccount = IcePy.Operation('createAccount', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), _M_Bank._t_PersonData, False, 0), ((), _M_Bank._t_MoneyAmount, False, 0)), (), ((), IcePy._t_string, False, 0), (_M_Bank._t_NotSupportedCurrencyException,))
    BankService._op_loginStandard = IcePy.Operation('loginStandard', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0), ((), IcePy._t_string, False, 0)), (), ((), _M_Bank._t_AccountPrx, False, 0), ())
    BankService._op_loginPremium = IcePy.Operation('loginPremium', Ice.OperationMode.Normal, Ice.OperationMode.Normal, False, None, (), (((), IcePy._t_string, False, 0), ((), IcePy._t_string, False, 0)), (), ((), _M_Bank._t_AccountPrx, False, 0), ())

    _M_Bank.BankService = BankService
    del BankService

# End of module Bank
