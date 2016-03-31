<#import "masterPage.ftl" as masterPage/>

<@masterPage.masterPage>

<h2>Lista wiadomości</h2>

<table class="table table-bordered">
    <tr>
        <th>ID</th>
        <th>Treść</th>
        <th>Właściciel</th>
        <th></th>
    </tr>
    <#list messages as message>
        <tr>
            <td>${message.id}</td>
            <td>${message.content}</td>
            <td>${message.owner.login}</td>
            <td>
                <#if USER??>
                    <#if message.owner.login==USER.login>
                        <div style="padding: 4px">
                            <form method="get" action="removeMessage">
                                <input type="hidden" name="messageId" value="${message.id}">
                                <button type="submit" class="btn btn-danger">usuń</button>
                            </form>
                        </div>
                        <div style="padding: 4px">
                            <form method="get" action="manageAllowance">
                                <input type="hidden" name="messageId" value="${message.id}">
                                <button type="submit" class="btn btn-info">zarządzaj uprawnieniami</button>
                            </form>
                        </div>
                    </#if>
                    <#if allowedMessages?seq_contains(message.id) || message.owner.login==USER.login>
                        <div style="padding: 4px">
                            <form method="get" action="editMessagePage">
                                <input type="hidden" name="messageId" value="${message.id}">
                                <button type="submit" class="btn btn-info">edytuj</button>
                            </form>
                        </div>
                    </#if>
                </#if>
            </td>
        </tr>
    </#list>
</table>



</@masterPage.masterPage>