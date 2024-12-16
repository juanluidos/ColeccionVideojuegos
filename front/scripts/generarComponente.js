const fs = require('fs');
const path = require('path');

const componentName = process.argv[2];
if (!componentName) {
  console.error("Por favor, proporciona un nombre para el componente.");
  process.exit(1);
}

const componentDir = path.join('src', 'components', componentName);
const componentFile = path.join(componentDir, `${componentName}.jsx`);

if (!fs.existsSync(componentDir)) {
  fs.mkdirSync(componentDir, { recursive: true });
}

const template = `import React from 'react';

const ${componentName} = () => {
  return (
    <div>
      <h1>${componentName}</h1>
    </div>
  );
};

export default ${componentName};
`;

fs.writeFileSync(componentFile, template, 'utf8');
console.log(`Componente ${componentName} generado en ${componentFile}`);
