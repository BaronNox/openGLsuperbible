#version 450 core

out vec4 color;

layout(std140, binding = 0) uniform ColorBlock {
	vec4 color;
} colorBlock;

void main() {
	color = colorBlock.color;
}
