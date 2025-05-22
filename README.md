# Todo-Summary-Assistant
Todo Summary Assistant
A full-stack application to manage personal to-do items, generate a meaningful summary of all pending to-dos using a real Large Language Model (LLM) API, and send that summary to a Slack channel via Slack Incoming Webhooks.

Features
Create, edit, and delete personal to-do items.

View a list of all current to-dos.

Generate a summary of pending to-dos using a real LLM API.

Send the generated summary directly to a Slack channel.

Show success or failure feedback after sending the summary.

Tech Stack used--

Frontend	React
Backend	Springboot 
Database	Supabase (MySQL)
LLM API	OpenAI API 
Slack API	Slack Incoming Webhooks

Architecture Overview--

The React frontend interacts with the backend through RESTful APIs.

Backend endpoints handle CRUD operations for to-dos, summarize pending to-dos using LLM API, and send the summary to Slack.

To-dos are stored in a PostgreSQL database hosted on Supabase.

Slack Incoming Webhooks integration posts the summary to a configured Slack channel.

Environment variables keep sensitive credentials secure.

API Endpoints--------

Method	Endpoint	Description
GET	/todos	Fetch all to-dos
POST	/todos	Add a new to-do
DELETE	/todos/:id	Delete a to-do by ID
POST	/summarize	Generate and send summary to Slack


Run the Application and check these endpoints in Postman
---------------------------------------------------

A Supabase account 

OpenAI API key (or other LLM API keys)

Slack workspace with Incoming Webhook URL

Setup Instructions

------------------Thank you---------------------------



