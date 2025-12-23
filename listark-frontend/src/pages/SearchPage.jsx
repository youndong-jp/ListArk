import { useState } from "react";
import { useNavigate } from "react-router-dom";

function SearchPage() {
  const [name, setName] = useState("");
  const navigate = useNavigate();

  const handleSearch = () => {
    if (!name.trim()) return;
    navigate(`/characters/${name}/armory`);
  };

  return (
    <div>
      <h1>ListArk</h1>

      <input
        value={name}
        onChange={(e) => setName(e.target.value)}
        placeholder="캐릭터 이름"
      />

      <button onClick={handleSearch}>조회</button>
    </div>
  );
}

export default SearchPage;
