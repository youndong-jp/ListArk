import "./Tabs.css";

function Tabs({ tabs = [], activeKey, onChange }) {
  if (!tabs.length) return null;

  const activeTab = tabs.find((tab) => tab.key === activeKey) ?? tabs[0];

  return (
    <div className="tabs">
      <div className="tabs-list">
        {tabs.map((tab) => (
          <button
            key={tab.key}
            type="button"
            className={`tab-button${
              tab.key === activeTab.key ? " is-active" : ""
            }`}
            onClick={() => onChange?.(tab.key)}
          >
            {tab.label}
          </button>
        ))}
      </div>

      <div className="tab-panel">{activeTab.content}</div>
    </div>
  );
}

export default Tabs;
