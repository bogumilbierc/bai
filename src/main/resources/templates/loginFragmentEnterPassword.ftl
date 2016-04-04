<#import "masterPage.ftl" as masterPage/>

<@masterPage.masterPage>

<h2>Logowanie</h2>

<form role="form" action="loginWithFragmentPasswordCheck" method="get">

    <input type="hidden" name="login" value="${login}">
    <#--<input type="hidden" name="mask" value="${mask}">-->

    <table style="margin: auto;">
        <tr>
            <#list 0..length as x>
                <td style="width: 20px">
                    <#if mask?seq_contains(x)>
                        <input style="width: 20px" type="text" name="password[${x}]">
                    <#else>
                        <input style="width: 20px; background-color: gray" type="text" value="*" name="password[${x}]"
                               readonly="readonly">
                    </#if>
                </td>
            </#list>
        </tr>
        <tr>
            <#list 0..length as x>
                <td style="width: 20px">
                ${x}
                </td>
            </#list>
        </tr>
    </table>

    <button style="margin: auto;display: block;" type="submit" class="btn btn-default">Zaloguj</button>
</form>
</@masterPage.masterPage>