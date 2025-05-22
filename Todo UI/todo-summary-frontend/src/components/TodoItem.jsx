import React from 'react'

const TodoItem = ({ todo, onDelete, onEdit }) => {
  return (
    <div className="todo-item">
      <span>{todo.text}</span>
      <div className="todo-actions">
        <button className="edit-btn" onClick={() => onEdit(todo)}>Edit</button>
        <button className="delete-btn" onClick={() => onDelete(todo._id)}>Delete</button>
      </div>
    </div>
  )
}

export default TodoItem
