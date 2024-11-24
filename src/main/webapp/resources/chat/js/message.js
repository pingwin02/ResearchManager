function receive(msg, channel, event) {
    let data = JSON.parse(event.data);
    let timestamp = new Date().toLocaleString();

    appendLine(formatMessage(data.from, data.content, timestamp, channel));
}

function formatMessage(sender, content, timestamp, channel) {
    let senderStyled = `<strong>${sender}</strong>`;
    let timestampStyled = `<span class="text-muted">${timestamp}</span>`;
    let contentStyled = `<div class="message-content">${content}</div>`;

    let messageClass = (channel === 'broadcast') ? 'message-broadcast' : 'message-private';

    let dmLabel = (channel !== 'broadcast') ? '<span class="dm-label">[DM]</span> ' : '';

    return `<div class="message ${messageClass}">
                <div class="message-header">
                    ${dmLabel}${senderStyled} <span class="timestamp">${timestampStyled}</span>
                </div>
                ${contentStyled}
            </div>`;
}


function appendLine(line) {
    let container = document.getElementById('message-container');
    container.innerHTML += line;
    container.scrollTop = container.scrollHeight;
}

document.addEventListener("DOMContentLoaded", function () {
    let messageInput = document.getElementById("form:message-input");
    let sendButton = document.getElementById("form:send-button");

    function updateSendButtonState() {
        if (messageInput.value.trim() === "") {
            sendButton.disabled = true;
        } else {
            sendButton.disabled = false;
        }
    }

    messageInput.addEventListener("input", updateSendButtonState);

    sendButton.addEventListener("click", function () {
        messageInput.value = "";
        updateSendButtonState();
    });

    updateSendButtonState();
});
