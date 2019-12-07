// **********************************************************************
//
// Copyright (c) 2003-2018 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.7.1
//
// <auto-generated>
//
// Generated from file `bank.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package Bank;

public interface BankService extends com.zeroc.Ice.Object
{
    String createAccount(PersonData personData, MoneyAmount monthIncome, com.zeroc.Ice.Current current)
        throws NotSupportedCurrencyException;

    AccountPrx loginStandard(String pesel, String password, com.zeroc.Ice.Current current);

    AccountPrx loginPremium(String pesel, String password, com.zeroc.Ice.Current current);

    static final String[] _iceIds =
    {
        "::Bank::BankService",
        "::Ice::Object"
    };

    @Override
    default String[] ice_ids(com.zeroc.Ice.Current current)
    {
        return _iceIds;
    }

    @Override
    default String ice_id(com.zeroc.Ice.Current current)
    {
        return ice_staticId();
    }

    static String ice_staticId()
    {
        return "::Bank::BankService";
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_createAccount(BankService obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        PersonData iceP_personData;
        MoneyAmount iceP_monthIncome;
        iceP_personData = PersonData.ice_read(istr);
        iceP_monthIncome = MoneyAmount.ice_read(istr);
        inS.endReadParams();
        String ret = obj.createAccount(iceP_personData, iceP_monthIncome, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeString(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_loginStandard(BankService obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_pesel;
        String iceP_password;
        iceP_pesel = istr.readString();
        iceP_password = istr.readString();
        inS.endReadParams();
        AccountPrx ret = obj.loginStandard(iceP_pesel, iceP_password, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeProxy(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    static java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceD_loginPremium(BankService obj, final com.zeroc.IceInternal.Incoming inS, com.zeroc.Ice.Current current)
    {
        com.zeroc.Ice.Object._iceCheckMode(null, current.mode);
        com.zeroc.Ice.InputStream istr = inS.startReadParams();
        String iceP_pesel;
        String iceP_password;
        iceP_pesel = istr.readString();
        iceP_password = istr.readString();
        inS.endReadParams();
        AccountPrx ret = obj.loginPremium(iceP_pesel, iceP_password, current);
        com.zeroc.Ice.OutputStream ostr = inS.startWriteParams();
        ostr.writeProxy(ret);
        inS.endWriteParams(ostr);
        return inS.setResult(ostr);
    }

    final static String[] _iceOps =
    {
        "createAccount",
        "ice_id",
        "ice_ids",
        "ice_isA",
        "ice_ping",
        "loginPremium",
        "loginStandard"
    };

    @Override
    default java.util.concurrent.CompletionStage<com.zeroc.Ice.OutputStream> _iceDispatch(com.zeroc.IceInternal.Incoming in, com.zeroc.Ice.Current current)
        throws com.zeroc.Ice.UserException
    {
        int pos = java.util.Arrays.binarySearch(_iceOps, current.operation);
        if(pos < 0)
        {
            throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
        }

        switch(pos)
        {
            case 0:
            {
                return _iceD_createAccount(this, in, current);
            }
            case 1:
            {
                return com.zeroc.Ice.Object._iceD_ice_id(this, in, current);
            }
            case 2:
            {
                return com.zeroc.Ice.Object._iceD_ice_ids(this, in, current);
            }
            case 3:
            {
                return com.zeroc.Ice.Object._iceD_ice_isA(this, in, current);
            }
            case 4:
            {
                return com.zeroc.Ice.Object._iceD_ice_ping(this, in, current);
            }
            case 5:
            {
                return _iceD_loginPremium(this, in, current);
            }
            case 6:
            {
                return _iceD_loginStandard(this, in, current);
            }
        }

        assert(false);
        throw new com.zeroc.Ice.OperationNotExistException(current.id, current.facet, current.operation);
    }
}
