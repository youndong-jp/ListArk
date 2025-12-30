import ProfileHeader from "./ProfileHeader";
import ProfileStats from "./ProfileStats";
import ProfileTendencies from "./ProfileTendencies";

function ProfileSection({ profile }) {
   console.log("[ProfileSection] profile:", profile);
  if (!profile) return null;

  return (
    <section>
      <ProfileHeader profile={profile} />
      <ProfileStats stats={profile.stats} />
      <ProfileTendencies tendencies={profile.tendencies} />
    </section>
  );
}

export default ProfileSection;
