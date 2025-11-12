import { useParams } from 'react-router-dom';
import { CrearPartidoForm } from '@/components/Partidos/CrearPartidoForm';

function CrearPartido() {
  const { torneoId } = useParams<{ torneoId: string }>();

  if (!torneoId) {
    return <div>Error: ID del torneo no proporcionado</div>;
  }

  return (
    <div className="container mx-auto py-6">
      <div className="flex flex-col gap-4">
        <h1 className="text-2xl font-bold">Crear Partido</h1>
        <CrearPartidoForm torneoId={Number(torneoId)} />
      </div>
    </div>
  );
}

export default CrearPartido;