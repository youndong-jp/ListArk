import { Routes, Route } from "react-router-dom";
import SearchPage from "./pages/SearchPage";
import ArmoryPage from "./pages/ArmoryPage";

function App() {
  return (
    <main>
      <Routes>
        <Route path="/" element={<SearchPage />} />
        <Route path="/characters/:name/armory" element={<ArmoryPage />} />
      </Routes>
    </main>
  );
}

export default App;
