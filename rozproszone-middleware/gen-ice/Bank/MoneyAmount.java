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

public class MoneyAmount implements java.lang.Cloneable,
                                    java.io.Serializable
{
    public long amount;

    public CurrencyIce currency;

    public MoneyAmount()
    {
        this.currency = CurrencyIce.PLN;
    }

    public MoneyAmount(long amount, CurrencyIce currency)
    {
        this.amount = amount;
        this.currency = currency;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        MoneyAmount r = null;
        if(rhs instanceof MoneyAmount)
        {
            r = (MoneyAmount)rhs;
        }

        if(r != null)
        {
            if(this.amount != r.amount)
            {
                return false;
            }
            if(this.currency != r.currency)
            {
                if(this.currency == null || r.currency == null || !this.currency.equals(r.currency))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::Bank::MoneyAmount");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, amount);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, currency);
        return h_;
    }

    public MoneyAmount clone()
    {
        MoneyAmount c = null;
        try
        {
            c = (MoneyAmount)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeLong(this.amount);
        CurrencyIce.ice_write(ostr, this.currency);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.amount = istr.readLong();
        this.currency = CurrencyIce.ice_read(istr);
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, MoneyAmount v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public MoneyAmount ice_read(com.zeroc.Ice.InputStream istr)
    {
        MoneyAmount v = new MoneyAmount();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<MoneyAmount> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, MoneyAmount v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<MoneyAmount> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(MoneyAmount.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final MoneyAmount _nullMarshalValue = new MoneyAmount();

    public static final long serialVersionUID = 1623465210L;
}