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

public interface BankServicePrx extends com.zeroc.Ice.ObjectPrx
{
    default String createAccount(PersonData personData, MoneyAmount monthIncome)
        throws NotSupportedCurrencyException
    {
        return createAccount(personData, monthIncome, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default String createAccount(PersonData personData, MoneyAmount monthIncome, java.util.Map<String, String> context)
        throws NotSupportedCurrencyException
    {
        try
        {
            return _iceI_createAccountAsync(personData, monthIncome, context, true).waitForResponseOrUserEx();
        }
        catch(NotSupportedCurrencyException ex)
        {
            throw ex;
        }
        catch(com.zeroc.Ice.UserException ex)
        {
            throw new com.zeroc.Ice.UnknownUserException(ex.ice_id(), ex);
        }
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> createAccountAsync(PersonData personData, MoneyAmount monthIncome)
    {
        return _iceI_createAccountAsync(personData, monthIncome, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<java.lang.String> createAccountAsync(PersonData personData, MoneyAmount monthIncome, java.util.Map<String, String> context)
    {
        return _iceI_createAccountAsync(personData, monthIncome, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<java.lang.String> _iceI_createAccountAsync(PersonData iceP_personData, MoneyAmount iceP_monthIncome, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<java.lang.String> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "createAccount", null, sync, _iceE_createAccount);
        f.invoke(true, context, null, ostr -> {
                     PersonData.ice_write(ostr, iceP_personData);
                     MoneyAmount.ice_write(ostr, iceP_monthIncome);
                 }, istr -> {
                     String ret;
                     ret = istr.readString();
                     return ret;
                 });
        return f;
    }

    static final Class<?>[] _iceE_createAccount =
    {
        NotSupportedCurrencyException.class
    };

    default AccountPrx loginStandard(String pesel, String password)
    {
        return loginStandard(pesel, password, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default AccountPrx loginStandard(String pesel, String password, java.util.Map<String, String> context)
    {
        return _iceI_loginStandardAsync(pesel, password, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<AccountPrx> loginStandardAsync(String pesel, String password)
    {
        return _iceI_loginStandardAsync(pesel, password, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<AccountPrx> loginStandardAsync(String pesel, String password, java.util.Map<String, String> context)
    {
        return _iceI_loginStandardAsync(pesel, password, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<AccountPrx> _iceI_loginStandardAsync(String iceP_pesel, String iceP_password, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<AccountPrx> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "loginStandard", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_pesel);
                     ostr.writeString(iceP_password);
                 }, istr -> {
                     AccountPrx ret;
                     ret = AccountPrx.uncheckedCast(istr.readProxy());
                     return ret;
                 });
        return f;
    }

    default AccountPrx loginPremium(String pesel, String password)
    {
        return loginPremium(pesel, password, com.zeroc.Ice.ObjectPrx.noExplicitContext);
    }

    default AccountPrx loginPremium(String pesel, String password, java.util.Map<String, String> context)
    {
        return _iceI_loginPremiumAsync(pesel, password, context, true).waitForResponse();
    }

    default java.util.concurrent.CompletableFuture<AccountPrx> loginPremiumAsync(String pesel, String password)
    {
        return _iceI_loginPremiumAsync(pesel, password, com.zeroc.Ice.ObjectPrx.noExplicitContext, false);
    }

    default java.util.concurrent.CompletableFuture<AccountPrx> loginPremiumAsync(String pesel, String password, java.util.Map<String, String> context)
    {
        return _iceI_loginPremiumAsync(pesel, password, context, false);
    }

    default com.zeroc.IceInternal.OutgoingAsync<AccountPrx> _iceI_loginPremiumAsync(String iceP_pesel, String iceP_password, java.util.Map<String, String> context, boolean sync)
    {
        com.zeroc.IceInternal.OutgoingAsync<AccountPrx> f = new com.zeroc.IceInternal.OutgoingAsync<>(this, "loginPremium", null, sync, null);
        f.invoke(true, context, null, ostr -> {
                     ostr.writeString(iceP_pesel);
                     ostr.writeString(iceP_password);
                 }, istr -> {
                     AccountPrx ret;
                     ret = AccountPrx.uncheckedCast(istr.readProxy());
                     return ret;
                 });
        return f;
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static BankServicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, ice_staticId(), BankServicePrx.class, _BankServicePrxI.class);
    }

    /**
     * Contacts the remote server to verify that the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static BankServicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, context, ice_staticId(), BankServicePrx.class, _BankServicePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static BankServicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, ice_staticId(), BankServicePrx.class, _BankServicePrxI.class);
    }

    /**
     * Contacts the remote server to verify that a facet of the object implements this type.
     * Raises a local exception if a communication error occurs.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @param context The Context map to send with the invocation.
     * @return A proxy for this type, or null if the object does not support this type.
     **/
    static BankServicePrx checkedCast(com.zeroc.Ice.ObjectPrx obj, String facet, java.util.Map<String, String> context)
    {
        return com.zeroc.Ice.ObjectPrx._checkedCast(obj, facet, context, ice_staticId(), BankServicePrx.class, _BankServicePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @return A proxy for this type.
     **/
    static BankServicePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, BankServicePrx.class, _BankServicePrxI.class);
    }

    /**
     * Downcasts the given proxy to this type without contacting the remote server.
     * @param obj The untyped proxy.
     * @param facet The name of the desired facet.
     * @return A proxy for this type.
     **/
    static BankServicePrx uncheckedCast(com.zeroc.Ice.ObjectPrx obj, String facet)
    {
        return com.zeroc.Ice.ObjectPrx._uncheckedCast(obj, facet, BankServicePrx.class, _BankServicePrxI.class);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the per-proxy context.
     * @param newContext The context for the new proxy.
     * @return A proxy with the specified per-proxy context.
     **/
    @Override
    default BankServicePrx ice_context(java.util.Map<String, String> newContext)
    {
        return (BankServicePrx)_ice_context(newContext);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the adapter ID.
     * @param newAdapterId The adapter ID for the new proxy.
     * @return A proxy with the specified adapter ID.
     **/
    @Override
    default BankServicePrx ice_adapterId(String newAdapterId)
    {
        return (BankServicePrx)_ice_adapterId(newAdapterId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoints.
     * @param newEndpoints The endpoints for the new proxy.
     * @return A proxy with the specified endpoints.
     **/
    @Override
    default BankServicePrx ice_endpoints(com.zeroc.Ice.Endpoint[] newEndpoints)
    {
        return (BankServicePrx)_ice_endpoints(newEndpoints);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator cache timeout.
     * @param newTimeout The new locator cache timeout (in seconds).
     * @return A proxy with the specified locator cache timeout.
     **/
    @Override
    default BankServicePrx ice_locatorCacheTimeout(int newTimeout)
    {
        return (BankServicePrx)_ice_locatorCacheTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the invocation timeout.
     * @param newTimeout The new invocation timeout (in seconds).
     * @return A proxy with the specified invocation timeout.
     **/
    @Override
    default BankServicePrx ice_invocationTimeout(int newTimeout)
    {
        return (BankServicePrx)_ice_invocationTimeout(newTimeout);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for connection caching.
     * @param newCache <code>true</code> if the new proxy should cache connections; <code>false</code> otherwise.
     * @return A proxy with the specified caching policy.
     **/
    @Override
    default BankServicePrx ice_connectionCached(boolean newCache)
    {
        return (BankServicePrx)_ice_connectionCached(newCache);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the endpoint selection policy.
     * @param newType The new endpoint selection policy.
     * @return A proxy with the specified endpoint selection policy.
     **/
    @Override
    default BankServicePrx ice_endpointSelection(com.zeroc.Ice.EndpointSelectionType newType)
    {
        return (BankServicePrx)_ice_endpointSelection(newType);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for how it selects endpoints.
     * @param b If <code>b</code> is <code>true</code>, only endpoints that use a secure transport are
     * used by the new proxy. If <code>b</code> is false, the returned proxy uses both secure and
     * insecure endpoints.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default BankServicePrx ice_secure(boolean b)
    {
        return (BankServicePrx)_ice_secure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the encoding used to marshal parameters.
     * @param e The encoding version to use to marshal request parameters.
     * @return A proxy with the specified encoding version.
     **/
    @Override
    default BankServicePrx ice_encodingVersion(com.zeroc.Ice.EncodingVersion e)
    {
        return (BankServicePrx)_ice_encodingVersion(e);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its endpoint selection policy.
     * @param b If <code>b</code> is <code>true</code>, the new proxy will use secure endpoints for invocations
     * and only use insecure endpoints if an invocation cannot be made via secure endpoints. If <code>b</code> is
     * <code>false</code>, the proxy prefers insecure endpoints to secure ones.
     * @return A proxy with the specified selection policy.
     **/
    @Override
    default BankServicePrx ice_preferSecure(boolean b)
    {
        return (BankServicePrx)_ice_preferSecure(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the router.
     * @param router The router for the new proxy.
     * @return A proxy with the specified router.
     **/
    @Override
    default BankServicePrx ice_router(com.zeroc.Ice.RouterPrx router)
    {
        return (BankServicePrx)_ice_router(router);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for the locator.
     * @param locator The locator for the new proxy.
     * @return A proxy with the specified locator.
     **/
    @Override
    default BankServicePrx ice_locator(com.zeroc.Ice.LocatorPrx locator)
    {
        return (BankServicePrx)_ice_locator(locator);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for collocation optimization.
     * @param b <code>true</code> if the new proxy enables collocation optimization; <code>false</code> otherwise.
     * @return A proxy with the specified collocation optimization.
     **/
    @Override
    default BankServicePrx ice_collocationOptimized(boolean b)
    {
        return (BankServicePrx)_ice_collocationOptimized(b);
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses twoway invocations.
     * @return A proxy that uses twoway invocations.
     **/
    @Override
    default BankServicePrx ice_twoway()
    {
        return (BankServicePrx)_ice_twoway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses oneway invocations.
     * @return A proxy that uses oneway invocations.
     **/
    @Override
    default BankServicePrx ice_oneway()
    {
        return (BankServicePrx)_ice_oneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch oneway invocations.
     * @return A proxy that uses batch oneway invocations.
     **/
    @Override
    default BankServicePrx ice_batchOneway()
    {
        return (BankServicePrx)_ice_batchOneway();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses datagram invocations.
     * @return A proxy that uses datagram invocations.
     **/
    @Override
    default BankServicePrx ice_datagram()
    {
        return (BankServicePrx)_ice_datagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, but uses batch datagram invocations.
     * @return A proxy that uses batch datagram invocations.
     **/
    @Override
    default BankServicePrx ice_batchDatagram()
    {
        return (BankServicePrx)_ice_batchDatagram();
    }

    /**
     * Returns a proxy that is identical to this proxy, except for compression.
     * @param co <code>true</code> enables compression for the new proxy; <code>false</code> disables compression.
     * @return A proxy with the specified compression setting.
     **/
    @Override
    default BankServicePrx ice_compress(boolean co)
    {
        return (BankServicePrx)_ice_compress(co);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection timeout setting.
     * @param t The connection timeout for the proxy in milliseconds.
     * @return A proxy with the specified timeout.
     **/
    @Override
    default BankServicePrx ice_timeout(int t)
    {
        return (BankServicePrx)_ice_timeout(t);
    }

    /**
     * Returns a proxy that is identical to this proxy, except for its connection ID.
     * @param connectionId The connection ID for the new proxy. An empty string removes the connection ID.
     * @return A proxy with the specified connection ID.
     **/
    @Override
    default BankServicePrx ice_connectionId(String connectionId)
    {
        return (BankServicePrx)_ice_connectionId(connectionId);
    }

    /**
     * Returns a proxy that is identical to this proxy, except it's a fixed proxy bound
     * the given connection.@param connection The fixed proxy connection.
     * @return A fixed proxy bound to the given connection.
     **/
    @Override
    default BankServicePrx ice_fixed(com.zeroc.Ice.Connection connection)
    {
        return (BankServicePrx)_ice_fixed(connection);
    }

    static String ice_staticId()
    {
        return "::Bank::BankService";
    }
}
