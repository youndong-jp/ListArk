function ProfileHeader({ profile }) {
  if (!profile) return null;

  return (
    <section>
      <img
        src={profile.characterImage}
        alt={profile.characterName}
        width={120}
      />

      <p>
          서버  {profile.serverName}</p>
      <p>
        직업 {profile.characterClass}</p>
      <p>Item Lv. {profile.itemLevel}</p>
    </section>
  );
}

export default ProfileHeader;
