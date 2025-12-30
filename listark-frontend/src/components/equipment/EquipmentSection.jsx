import EquipmentGrid from "./EquipmentGrid";
import { COMBAT_SLOTS, ACCESSORY_SLOTS } from "./constants";

function EquipmentSection({ equipment }) {
  if (!equipment || equipment.length === 0) return null;

  const combat = equipment.filter(item =>
    COMBAT_SLOTS.includes(item.slot)
  );

  const accessories = equipment.filter(item =>
    ACCESSORY_SLOTS.includes(item.slot)
  );

  return (
    <section>
      <h2>장비</h2>

      <EquipmentGrid items={combat} />

      <h3>악세사리</h3>
      <EquipmentGrid items={accessories} />
    </section>
  );
}

export default EquipmentSection;
