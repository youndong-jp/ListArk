function EquipmentItem({ item }) {
  return (
    <div className={`equipment-item grade-${item.grade}`}>
      <img src={item.icon} alt={item.name} width={48} />

      <div>
        <div>{item.name}</div>
        {item.itemLevel && <div>{item.itemLevel}</div>}
        {item.qualityValue >= 0 && (
          <div>Quality {item.qualityValue}</div>
        )}
      </div>
    </div>
  );
}

export default EquipmentItem;
