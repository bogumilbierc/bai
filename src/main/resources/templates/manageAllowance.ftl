<#import "masterPage.ftl" as masterPage/>

<@masterPage.masterPage>

<h2>Zarządzaj wiadomością</h2>

<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>Treść</th>
        <th>Właściciel</th>
        <th></th>
    </tr>
    <tr>
        <td>${message.id}</td>
        <td>${message.content}</td>
        <td>${message.owner.login}</td>
        <td>
            <div style="padding: 4px">
                <form method="get" action="removeMessage">
                    <input type="hidden" name="messageId" value="${message.id}">
                    <button type="submit" class="btn btn-danger">usuń</button>
                </form>
            </div>
        </td>
    </tr>

</table>

<h3>Użytkownicy z dostępem</h3>
<table class="table table-bordered">
    <#list message.users as user>
        <tr>
            <td>${user.login}</td>
            <td style="padding: 3px">
                <form method="get" action="revokeAccess">
                    <input type="hidden" name="messageId" value="${message.id}">
                    <input type="hidden" name="userId" value="${user.id}">
                    <button type="submit" class="btn btn-submit">zabierz</button>
                </form>
            </td>
        </tr>
    </#list>
</table>

<h3>Nadaj dostęp</h3>

<form action="grantAccess" method="get">
    <input type="hidden" name="messageId" value="${message.id}">
    <div class="form-group">
        <select required class="form-control" name="userId">
            <#list notAllowedUsers as user>
                <option value="${user.id}">${user.login}</option>
            </#list>
        </select>
    </div>
    <button type="submit" class="btn btn-submit">nadaj</button>
</form>

</@masterPage.masterPage>