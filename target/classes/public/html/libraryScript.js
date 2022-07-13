let baseUrl = "http://localhost:8080"; // /users, /users/1

async function login() {
    console.log("login button pressed")

    // gather input from the user - using our DOM
    let uname = document.getElementById('uname').value;

    let pass = document.getElementById('pass').value;

    // create an object literal 
    let user = {
        username: uname,
        password: pass
    }

    // print that to the console
    console.log(user);

    // we need to convert the user object literal to a Json string
    // so we can send it in the body of our request
    let userJson = JSON.stringify(user);
    console.log(userJson);
    // send a POST request to our backend using the Fetch API
    // fetch method returns a Promise
    let res = await fetch(
                        `${baseUrl}/login`, // the url where we're sending this request
                        {
                            method: 'POST',
                            header: {'Content-Type': 'application/json'},
                            body: userJson
                        }
                    );
    let resJson = await res.json()
        // .then will execute if the promise is successfully resolved
        // .then() takes a function as an argument
        .then((resp) => {
            console.log(resp); // this is where we will eventually put our DOM manipulation if needed
            window.location.assign("homePage.html");
        })
        // .catch will execute if there's any error
        .catch((error) => {
            console.log(error);
        });


    
}


function submitReview() {
    let reviewText = document.getElementById('reviewText').value;

    let selection = document.getElementById('select').value;

    let bookReview = {
        text: reviewText,
        rating: selection
    }
    console.log(bookReview);
}