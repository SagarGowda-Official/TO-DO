import React, { useState } from "react";

function TodoList({ todos, handleDelete, handleEdit }) {
  const [editId, setEditId] = useState(null);
  const [editTask, setEditTask] = useState("");

  const startEdit = (todo) => {
    setEditId(todo.id);
    setEditTask(todo.task);
  };

  const saveEdit = () => {
    if (editTask.trim() === "") return;
    handleEdit(editId, editTask);
    setEditId(null);
    setEditTask("");
  };

  const cancelEdit = () => {
    setEditId(null);
    setEditTask("");
  };

  return (
    <ul className="list-group mt-3">
      {todos.map((todo) => (
        <li
          key={todo.id}
          className="list-group-item d-flex justify-content-between align-items-center"
        >
          {editId === todo.id ? (
            <div className="w-100 d-flex">
              <input
                type="text"
                className="form-control me-2"
                value={editTask}
                onChange={(e) => setEditTask(e.target.value)}
              />
              <button onClick={saveEdit} className="btn btn-success btn-sm me-1">
                Save
              </button>
              <button onClick={cancelEdit} className="btn btn-secondary btn-sm">
                Cancel
              </button>
            </div>
          ) : (
            <>
              <span>{todo.task}</span>
              <div>
                <button
                  onClick={() => startEdit(todo)}
                  className="btn btn-warning btn-sm me-2"
                >
                  Edit
                </button>
                <button
                  onClick={() => handleDelete(todo.id)}
                  className="btn btn-danger btn-sm"
                >
                  Delete
                </button>
              </div>
            </>
          )}
        </li>
      ))}
    </ul>
  );
}

export default TodoList;
