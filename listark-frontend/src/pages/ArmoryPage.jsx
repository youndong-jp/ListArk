import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

function ArmoryPage() {
  const { name } = useParams();

  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchArmory = async () => {
      try {
        setLoading(true);
        setError(null);

        const res = await fetch(
          `http://127.0.0.1:8080/api/characters/${name}/armory`
        );

        if (!res.ok) {
          throw new Error(`HTTP ${res.status}`);
        }

        const json = await res.json();
        setData(json.data);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };

    fetchArmory();
  }, [name]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h2>Armory Result</h2>
      <p>Character: {name}</p>
      <pre>{JSON.stringify(data, null, 2)}</pre>
    </div>
  );
}

export default ArmoryPage;
