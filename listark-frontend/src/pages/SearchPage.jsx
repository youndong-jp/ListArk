import { useEffect, useMemo, useState } from "react";
import CharacterCard from "../components/CharacterCard";
import SearchForm from "../components/SearchForm";
import Tabs from "../components/Tabs";
import "./SearchPage.css";

function SearchPage() {
  const [name, setName] = useState("");
  const [result, setResult] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [activeTab, setActiveTab] = useState("profile");

  const search = async () => {
    setLoading(true);
    setError(null);
    setResult(null);

    try {
      const res = await fetch(`/api/characters/${name}/armory`);

      if (!res.ok) {
        throw new Error(`HTTP error! status: ${res.status}`);
      }

      const data = await res.json();
      console.log("API Response:", data);
      setResult(data);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  };

  const tabs = useMemo(() => {
    if (!result?.data) return [];

    return [
      {
        key: "profile",
        label: "프로필",
        content: (
          <CharacterCard
            profile={result.data.profile}
            equipment={result.data.equipment}
          />
        ),
      },
    ];
  }, [result]);

  useEffect(() => {
    if (tabs.length === 0) {
      setActiveTab("profile");
      return;
    }

    setActiveTab((current) => {
      const stillExists = tabs.some((tab) => tab.key === current);
      return stillExists ? current : tabs[0].key;
    });
  }, [tabs]);

  return (
    <div className="search-page">
      <h1>ListArk</h1>

      <SearchForm
        name={name}
        onNameChange={setName}
        onSubmit={search}
        loading={loading}
      />

      {loading && <p>로딩중...</p>}
      {error && <p className="error-message">{error}</p>}

      {tabs.length > 0 && (
        <Tabs
          tabs={tabs}
          activeKey={activeTab}
          onChange={setActiveTab}
        />
      )}
    </div>
  );
}

export default SearchPage;
