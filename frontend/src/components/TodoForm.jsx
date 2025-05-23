import React from "react";

function TodoForm({ task, setTask, handleAdd }) {
  return (
    <div>
      <input
        value={task}
        onChange={(e) => setTask(e.target.value)}
        placeholder="Enter task"
        className="form-control"
      />
      <button onClick={handleAdd} className="btn btn-primary mt-2">
        Add Todo
      </button>
    </div>
  );
}

export default TodoForm;
