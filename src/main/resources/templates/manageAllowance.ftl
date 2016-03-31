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
    <#list message.users as user>
    <ul>
        <li>${user.login}</li>
    </ul>
    </#list>

<h3>Nadaj dostęp</h3>

</@masterPage.masterPage>