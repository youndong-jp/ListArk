import "./CharacterCard.css";

const EQUIPMENT_SLOTS = ["무기", "투구", "상의", "하의", "장갑", "어깨"];

function CharacterCard({ profile, equipment }) {
  if (!profile) return null;

  const equipmentArray = Array.isArray(equipment) ? equipment : [];
  const mainEquipment = equipmentArray.filter((item) =>
    EQUIPMENT_SLOTS.includes(item.slot)
  );
  const accessoryEquipment = equipmentArray.filter(
    (item) => !EQUIPMENT_SLOTS.includes(item.slot)
  );

  return (
    <div className="character-card">
      <img
        className="character-card-thumbnail"
        src={profile.characterImage}
        alt="character"
      />

      <div className="character-info">
        <h2>{profile.characterName}</h2>
        <p>직업: {profile.characterClass}</p>
        <p>서버: {profile.serverName}</p>
        <p>아이템 레벨: {profile.itemLevel}</p>

        {(mainEquipment.length > 0 || accessoryEquipment.length > 0) && (
          <div className="character-equipment">
            {mainEquipment.length > 0 && (
              <div className="character-equipment-row">
                <h3>장비</h3>
                <ul>
                  {mainEquipment.map((item, index) => (
                    <li key={`${item.slot}-${item.name}-${index}`}>
                      <img src={item.icon} alt={item.name} />
                      <div>
                        <div className="equipment-name">
                          <span className="equipment-slot">{item.slot}</span>
                          <span>{item.name}</span>
                        </div>
                      </div>
                    </li>
                  ))}
                </ul>
              </div>
            )}
            {accessoryEquipment.length > 0 && (
              <div className="character-equipment-row">
                <h3>장신구</h3>
                <ul>
                  {accessoryEquipment.map((item, index) => (
                    <li key={`${item.slot}-${item.name}-${index}`}>
                      <img src={item.icon} alt={item.name} />
                      <div>
                        <div className="equipment-name">
                          <span className="equipment-slot">{item.slot}</span>
                        </div>
                        <div className="equipment-meta">
                          {item.grade && <span>{item.grade}</span>}
                          {item.durability && <span>{item.durability}</span>}
                        </div>
                        {Array.isArray(item.effectList) &&
                          item.effectList.length > 0 && (
                            <div className="equipment-effects">
                              {item.effectList.map((effect, i) => {
                                const lines =
                                  typeof effect.content === "string"
                                    ? effect.content.split("\n")
                                    : [];

                                return (
                                  <div
                                    key={`${effect.title}-${i}`}
                                    className="equipment-effect-block"
                                  >
                                    <div className="equipment-effect-title">
                                      {effect.title}
                                    </div>
                                    {lines.length > 0 && (
                                      <ul>
                                        {lines.map((line) => (
                                          <li key={line}>{line}</li>
                                        ))}
                                      </ul>
                                    )}
                                  </div>
                                );
                              })}
                            </div>
                          )}
                      </div>
                    </li>
                  ))}
                </ul>
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default CharacterCard;

