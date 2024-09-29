# Design for Post Office

## The application of the project

The project is designed based on *the model of a post office*, and the project will:
- Allow user to send **different items**, like mails and parcels.
- **Reject** the request if the item is not allowed to be sent.
- Charge for different items **based on their height, wight and categories**.
- **Deposit** the items that is ready to send **before payment is done**.
- **Assign** different items or **a list of items** to different people **by their name**.

## The user this project is applicable to

This project is based on a model of post office, so the mail station is the domain user of this project,
and the function of this project is also applicable for banks or hotel who has the similar function requirement.

## The reason why this project interests me

I kept sending my friends beautiful postcard during last semester, and I am interested in how post office can
manage mails so well, after I consult the manager of a post office and getting familiar with Java now, 
I do think Java is a good way for me to transfer what I have known to a real project that can
simulate the function of a real post office.

## User stories

Based on my preliminary ideas

- As a user, I want to be able to **specify the category, weight of the items**.
- As a user, I want to determine **whether an item is accepted** by checking its weight and category.
- As a user, I want to be able to **assign the cost of an item** by its category and weight.
- As a user, I want to be able to **get an postmark as a proof** after the payment is done.
- As a user, I want to be able to **add person to a list** consists by people who received a mail.
- As a user, I want to view the mails waiting for the claim of **a specified person by name**.
- As a user, I want to be able to **remove mails from the office** when they are claimed.
- As a user, I want to be able to **see the number of mails** I have received so far, and how many of them **haven't
  been claimed yet**.
- As a user, when I select the quit option from the application menu, I want to be reminded 
  to save the **state of parcel** and **each person's ready parcel list** to file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option 
  to load **parcels** and **each person's parcel list** from file.


## Phase 4: Task 3

- If I had more time to work on the project, I may refactor the PostOffice and PostOfficeGUI.
Now the parcel and person numbers are limited, which means we can't register a new person as a receiver 
nor add a new parcel to this post office to increase the parcels numbers if we want.
- In the future if I can refactor this project, I would refactor the association between Person and 
PostOffice, Parcel and PostOffice to make inputting and registering parcels and persons available.
 

 