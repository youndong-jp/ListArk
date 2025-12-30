function ProfileStats({ stats }) {
  if (!stats) return null;

  return (
    <section>
      <h3>Stats</h3>
      <ul>
        {Object.entries(stats).map(([type, value]) => (
          <li key={type}>
            <strong>{type}</strong>: {value}
          </li>
        ))}
      </ul>
    </section>
  );
}

export default ProfileStats;
