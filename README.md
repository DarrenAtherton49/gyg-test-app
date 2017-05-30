# GYG App

## To Do
- Allow for pagination of results either via a button or a `RecyclerView` `onScrollListener`.
- Allow user to filter results.

## Submit Review Request
- Shown as an interface method in `ReviewService`
- Takes a `ReviewData` and returns a `SubmitReviewResponse`
- Example payload: `{ "review_id" : null, "title" : "MyTitle", "message" : "MyMessage" : "author" : "TheAuthor", "date" : "25 May, 2017" }`