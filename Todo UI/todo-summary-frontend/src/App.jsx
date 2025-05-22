import React, { useEffect, useState } from "react";
import axios from "axios";
import { ToastContainer, toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

const baseUrl = "http://localhost:8080/todos";

function App() {
  const [todos, setTodos] = useState([]);
  const [newTodoTitle, setNewTodoTitle] = useState("");
  const [loading, setLoading] = useState(false);

  const fetchTodos = async () => {
    try {
      const res = await axios.get(baseUrl);
      setTodos(res.data);
    } catch (err) {
      toast.error("Error fetching todos");
    }
  };

  const addTodo = async () => {
    if (!newTodoTitle.trim()) return toast.warning("Please enter a todo title.");
    try {
      await axios.post(baseUrl, { title: newTodoTitle, completed: false });
      setNewTodoTitle("");
      fetchTodos();
      toast.success("Todo added!");
    } catch (err) {
      toast.error("Failed to add todo");
    }
  };

  const deleteTodo = async (id) => {
    try {
      await axios.delete(`${baseUrl}/${id}`);
      fetchTodos();
      toast.info("Todo deleted");
    } catch (err) {
      toast.error("Failed to delete todo");
    }
  };

  const summarizeAndSend = async () => {
    try {
      setLoading(true);
      const res = await axios.post(`${baseUrl}/summarize`);
      toast.success(res.data.message);
    } catch (err) {
      toast.error("Failed to send summary to Slack.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTodos();
  }, []);

  return (
    <div style={styles.container}>
      <ToastContainer />
      <h1 style={styles.heading}>📝 Todo App</h1>

      <div style={styles.inputContainer}>
        <input
          type="text"
          value={newTodoTitle}
          placeholder="Enter a new todo..."
          onChange={(e) => setNewTodoTitle(e.target.value)}
          style={styles.input}
        />
        <button onClick={addTodo} style={styles.button}>
          ➕ Add
        </button>
      </div>

      <ul style={styles.list}>
        {todos.map((todo) => (
          <li key={todo.id} style={styles.listItem}>
            {todo.title}
            <button onClick={() => deleteTodo(todo.id)} style={styles.deleteButton}>
              ❌
            </button>
          </li>
        ))}
      </ul>

      <button onClick={summarizeAndSend} style={styles.summaryButton} disabled={loading}>
        {loading ? "Sending..." : "📩 Summarize & Send to Slack"}
      </button>
    </div>
  );
}

const styles = {
  container: {
    padding: "40px",
    maxWidth: "600px",
    margin: "auto",
    fontFamily: "Arial, sans-serif",
  },
  heading: {
    textAlign: "center",
    marginBottom: "30px",
  },
  inputContainer: {
    display: "flex",
    marginBottom: "20px",
  },
  input: {
    flex: 1,
    padding: "10px",
    fontSize: "16px",
  },
  button: {
    padding: "10px 15px",
    marginLeft: "10px",
    fontSize: "16px",
    backgroundColor: "#4caf50",
    color: "#fff",
    border: "none",
    cursor: "pointer",
  },
  list: {
    listStyle: "none",
    padding: 0,
  },
  listItem: {
    background: "#f5f5f5",
    padding: "10px",
    marginBottom: "10px",
    display: "flex",
    justifyContent: "space-between",
  },
  deleteButton: {
    backgroundColor: "#e53935",
    color: "#fff",
    border: "none",
    cursor: "pointer",
    padding: "5px 10px",
  },
  summaryButton: {
    marginTop: "20px",
    padding: "12px 20px",
    fontSize: "16px",
    backgroundColor: "#2196f3",
    color: "#fff",
    border: "none",
    cursor: "pointer",
    width: "100%",
  },
};

export default App;
