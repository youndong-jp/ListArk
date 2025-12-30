function ProfileTendencies({ tendencies }) {
  if (!tendencies) return null;

  return (
    <section>
      <h3>Tendencies</h3>
      <ul>
        {Object.entries(tendencies).map(([type, value]) => (
          <li key={type}>
            {type}: {value}
          </li>
        ))}
      </ul>
    </section>
  );
}

export default ProfileTendencies;
