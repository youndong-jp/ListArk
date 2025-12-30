import EquipmentItem from "./EquipmentItem";

function EquipmentGrid({ items }) {
  return (
    <div className="equipment-grid">
      {items.map((item, idx) => (
        <EquipmentItem key={`${item.slot}-${idx}`} item={item} />
      ))}
    </div>
  );
}

export default EquipmentGrid;
