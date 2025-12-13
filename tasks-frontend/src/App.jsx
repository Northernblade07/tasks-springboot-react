import { BrowserRouter, Routes, Route } from "react-router-dom";
import TaskListsPage from "./pages/TaskListsPage";
import TasksPage from "./pages/TasksPage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<TaskListsPage />} />
        <Route path="/task-lists/:taskListId" element={<TasksPage />} />
      </Routes>
    </BrowserRouter>
  );
}
