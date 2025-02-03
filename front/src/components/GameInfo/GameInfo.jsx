import "./GameInfo.css"
import React, { useState } from "react";
import { Button, Modal,Switch,Timeline   } from 'antd'
const GameInfo = ({ game }) => {
	const [isModalOpen, setIsModalOpen] = useState([false, false]);
	const gamesupport = game.soporte
	const gameProgress = game.progreso.map(item => ({children:item}))
	const butonOnClick = (idx, target) => {
		setIsModalOpen((p) => {
			p[idx] = target;
			return [...p];
		});
	};

	return (
		<div className="gameInfo" >
			<div className="gameTitle">
				<h1 >
					{game.nombre}
				</h1>
			</div>
			<div>
				<div className="gameInfoRow">
					<span>
						Precio:
					</span>
					<span>{game.precio}
					</span>
				</div>

				<div className="gameInfoRow">
					<span>
						fecha de lanzamiento:
					</span>
					<span>
						{game.fechaLanzamiento}
					</span>
				</div>

				<div className="gameInfoRow">
					<span>
						Plataforma:
					</span>
					<span>
						{game.plataforma}
					</span>
				</div>

				<div className="gameInfoRow">
					<span>
						Genero:
					</span>
					<span>
						{game.genero}
					</span>
				</div>

				<div className="gameInfoRow">
					<span>
						Progreso:
					</span>
					<span>
						{game.progreso.at(-1)}
					</span>
					<div>
					<Button onClick={
							(e) => {
								e.stopPropagation();
								butonOnClick(0, true)
							}}>Detalles</Button>
					</div>
				</div>
				<Modal
					title="Detalles del soporte"
					cancelButtonProps={{ style: { display: 'none' } }}
					open={isModalOpen[0]}
					centered={true}
					closable={false}
					onOk={(e) => {
						e.stopPropagation();
						butonOnClick(0, false)
					}}
				>
					<div className="statusBar">
						<Timeline items={gameProgress}></Timeline>
					</div>
				</Modal>
				<div className="gameInfoRow">
					<span>
						Soporte:
					</span>
					<span>
						({game.soporte.tipo} +  {game.soporte.estado})
					</span>
					<div>
						<Button onClick={
							(e) => {
								e.stopPropagation();
								butonOnClick(1, true)
							}}>Detalles</Button>
					</div>
				</div>
				<Modal
					title="Detalles del soporte"
					cancelButtonProps={{ style: { display: 'none' } }}
					open={isModalOpen[1]}
					centered={true}
					closable={false}
					onOk={(e) => {
						e.stopPropagation();
						butonOnClick(1, false)
					}}
				>
					<div>
						<div className="gameInfoRow">
							<span>
								Tipo:
							</span>
							<span>
								{gamesupport.tipo}
							</span>
						</div>
						<div className="gameInfoRow">
							<span>
								Estado:
							</span>
							<span>
								{gamesupport.estado}
							</span>
						</div>
						<div className="gameInfoRow">
							<span>
								Edicion:
							</span>
							<span>
								{gamesupport.edicion}
							</span>
						</div>
						<div className="gameInfoRow">
							<span>
								Distribucion:
							</span>
							<span>
								{gamesupport.distribucion}
							</span>
						</div>
						<div className="gameInfoRow">
							<span>
								Precintado:
							</span>
							<Switch disabled={true} checked = {gamesupport.precintado}></Switch>

						</div>
						<div className="gameInfoRow">
							<span>
								Region:
							</span>
							<span>
								{gamesupport.region}
							</span>
						</div>
						<div className="gameInfoRow">
							<span>
								Año de salida:
							</span>
							<span>
								{gamesupport.anyoSalidaDist}
							</span>
						</div>
						<div className="gameInfoRow">
							<span>
								Tienda:
							</span>
							<span>
								{gamesupport.tienda}
							</span>
						</div>
					</div>
				</Modal>


			</div>
		</div>
	)
}

export default GameInfo;