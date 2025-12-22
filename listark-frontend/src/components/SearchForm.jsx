import "./SearchForm.css";

function SearchForm({ name, onNameChange, onSubmit, loading }) {
  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit();
  };

  return (
    <form className="search-form" onSubmit={handleSubmit}>
      <input
        value={name}
        onChange={(e) => onNameChange(e.target.value)}
        placeholder="캐릭터 이름"
      />
      <button type="submit" onClick={onSubmit} disabled={loading}>
        {loading ? "조회 중..." : "조회"}
      </button>
    </form>
  );
}

export default SearchForm;

