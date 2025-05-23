import React, { useEffect, useState } from "react";
import axios from "axios";
import TodoForm from "./components/TodoForm";
import TodoList from "./components/TodoList";
import SummaryButton from "./components/SummaryButton";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "bootstrap/dist/css/bootstrap.min.css";

function App() {
  const [task, setTask] = useState("");
  const [todos, setTodos] = useState([]);

  const API_BASE = "http://localhost:8080/api";

  // Fetch todos from backend
  const fetchTodos = () => {
    axios
      .get(`${API_BASE}/todos`)
      .then((response) => setTodos(response.data))
      .catch((error) => {
        console.error(error);
        toast.error("Failed to load todos.");
      });
  };

  // Add new todo
  const handleAdd = () => {
    if (task.trim() === "") return;
    axios
      .post(`${API_BASE}/todos`, { task })
      .then(() => {
        setTask("");
        fetchTodos();
      })
      .catch(() => toast.error("Failed to add todo."));
  };

  // Delete todo
  const handleDelete = (id) => {
    axios
      .delete(`${API_BASE}/todos/${id}`)
      .then(fetchTodos)
      .catch(() => toast.error("Failed to delete todo."));
  };

  // Edit todo
  const handleEdit = (id, updatedTask) => {
    axios
      .put(`${API_BASE}/todos/${id}`, { task: updatedTask })
      .then(fetchTodos)
      .catch(() => toast.error("Failed to update todo."));
  };

  // Send summary to Slack
  const handleSummarize = () => {
    axios
      .post(`${API_BASE}/summarize`)
      .then(() => toast.success("Summary sent to Slack!"))
      .catch(() => toast.error("Failed to send summary."));
  };

  useEffect(fetchTodos, []);

  return (
    <div className="container mt-5">
      <h2>Todo Summary Assistant</h2>
      <TodoForm task={task} setTask={setTask} handleAdd={handleAdd} />
      <TodoList todos={todos} handleDelete={handleDelete} handleEdit={handleEdit} />
      <SummaryButton handleSummarize={handleSummarize} />
      <ToastContainer />
    </div>
  );
}

export default App;
