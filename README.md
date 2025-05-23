# Todo Summary Assistant

Overview
This project is a full-stack Todo app with the ability to generate summarized todo lists using OpenAI API and send the summary to Slack.

---
Setup Instructions

Backend
1. Clone the repo
2. Navigate to `backend/`
3. Create `.env` file using `.env.example`
4. Add your OpenAI API key and Slack webhook URL to `.env`
5. Run with Maven or your IDE

Frontend
1. Navigate to `frontend/`
2. Run `npm install`
3. Run `npm start` to launch React app
4. Make sure API base URL is correct in `.env` or `src`

---

Slack and LLM Setup Guidance

- Get a Slack Incoming Webhook URL by creating a Slack App and enabling incoming webhooks.
- Create an OpenAI account, get your API key from https://platform.openai.com/account/api-keys.
- Add those keys to your backend `.env`.

---

Design/Architecture Decisions

- Backend is Spring Boot REST API handling todo CRUD and summary generation.
- Frontend is React with Axios for API calls.
- SummaryHelper handles OpenAI API calls and Slack notifications.
- Environment variables used for secrets.
- CORS enabled on backend for React local development.
- Toast notifications in frontend for user feedback.



