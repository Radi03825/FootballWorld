const playerId = document.getElementById('playerId').value

const commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", handleFormSubmission)

const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

const commentContainer = document.getElementById('commentContainer')

async function handleFormSubmission(event) {
    event.preventDefault()

    const messageVal = document.getElementById('message').value

    if (messageVal.trim() !== '') {

        fetch(`https://footballlworld.herokuapp.com//api/${playerId}/comments`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json',
                [csrfHeaderName]: csrfHeaderValue
            },
            body: JSON.stringify({
                message: messageVal
            })
        }).then(res => res.json())
            .then(data => {
                document.getElementById('message').value = ""
                commentContainer.innerHTML += commentAsHtml(data)
            })
    } else {
        alert("Comment should not be empty!")
    }
}

function commentAsHtml(comment) {
    let commentHtml = '<hr>\n'
    commentHtml += '<div>\n'
    commentHtml += '<div class="row">\n'
    commentHtml += `<h4 class="col">${comment.username}</h4>\n`
    commentHtml += `<span class="col-auto">(${comment.created})</span>\n`
    commentHtml += '</div>\n'
    commentHtml += `<p>${comment.message}</p>\n`
    commentHtml += '</div>\n'

    return commentHtml
}

fetch(`https://footballlworld.herokuapp.com//api/${playerId}/comments`, {
    headers: {
        "Accept": "application/json"
    }
}).then(res => res.json())
    .then(data => {
        for(let comment of data) {
            commentContainer.innerHTML += commentAsHtml(comment)
        }
    })