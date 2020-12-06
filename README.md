# TP_MicroService_TLM

# Creators

* MALHERBE Timoté
* LORTET César
* TOURNATORY Loïc
                
# Architecture
![archi_micro_service](https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fwww.thoughtco.com%2Fthmb%2Fs-sGgR7zQSq2tZlZMlD7uuY81Gk%3D%2F7360x4912%2Ffilters%3Afill(auto%2C1)%2Fhappy-red-panda-171399380-5b574325c9e77c005b690b41.jpg&f=1&nofb=1)

# Routes
## Author-Service:
#### GET
- /authors
- /authors/{id}
- /authors/pseudo/{pseudo}
#### POST
- /authors/new
{ String pseudo }
- /authors
{ Author author }
- /authors/{authorsId}/add-quote
{ int quoteId }
#### PUT
#### DELETE
- /authors/{id}

## Comment-Service: 
#### GET
- /comments
- /comments/{id}
- /comments/{author}
- /comments/{quote}
#### POST
- /comments/new
{ String Author = "Anonymous", String Content }
- /comments/
{ Comment comment }
#### PUT
- /comments/{id}/edit
{ String Content }
#### DELETE
- /comments/{id}

## Quote-Service:
#### GET
- /quotes
- /quotes/{id}
- /quotes/author/{author_id}
#### POST
- /quotes/new
{ String Content }
- /quotes/
{ Quote quote }
- /quotes/{id}/add-comment/
{ int comment_id }
#### PUT
- /quotes/{id}/edit/content
{ String Content }
- /quotes/{id}/edit/upvote
- /quotes/{id}/edit/downvote
#### DELETE
- /quotes/{id}
