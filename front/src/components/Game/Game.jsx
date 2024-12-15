import React , {useState} from 'react';
import './Game.css';

const Game = ({title, platform, }) => {
	const [isExpanded, setIsExpanded] = useState(false);

    const handleClick = () => {
        setIsExpanded(!isExpanded);
    };
    return (
		<div className='gameBody'>
			{/* cara de frente */}
			{/* <div className={` game `}>
		     	<div  className={` ${platform}`}>
					<img></img>
				</div>
				<div className='details'>

					<span> {title} </span>
				</div>
		 	</div> */}
			<div className= {`frontal ${isExpanded ? 'clicked' : ''}`} onClick={handleClick}>
			</div>
			<div className= {`rigth ${isExpanded ? 'clicked' : ''}`} onClick={handleClick}>
			</div>
		</div>
       
		
    );
};

export default Game;
