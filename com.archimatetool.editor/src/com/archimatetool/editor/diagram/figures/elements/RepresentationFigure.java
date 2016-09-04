/**
 * This program and the accompanying materials
 * are made available under the terms of the License
 * which accompanies this distribution in the file LICENSE.txt
 */
package com.archimatetool.editor.diagram.figures.elements;

import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.graphics.Path;
import org.eclipse.swt.graphics.Pattern;

import com.archimatetool.editor.diagram.figures.AbstractTextControlContainerFigure;
import com.archimatetool.editor.diagram.figures.FigureUtils;
import com.archimatetool.editor.preferences.IPreferenceConstants;
import com.archimatetool.editor.preferences.Preferences;




/**
 * Representation Figure
 * 
 * @author Phillip Beauvoir
 */
public class RepresentationFigure extends AbstractTextControlContainerFigure {
    
    protected static final int TOP_MARGIN = 12;

    public RepresentationFigure() {
        super(TEXT_FLOW_CONTROL);
    }
    
    @Override
    public void drawFigure(Graphics graphics) {
        graphics.pushState();
        
        Rectangle bounds = getBounds().getCopy();
        
        int offset = 6;
        int curve_y = bounds.y + bounds.height - offset;
        
        if(!isEnabled()) {
            setDisabledState(graphics);
        }
        
        // Main Fill
        Path path = new Path(null);
        path.moveTo(bounds.x, bounds.y);
        path.lineTo(bounds.x, curve_y - 1);
        
        path.quadTo(bounds.x + (bounds.width / 4), bounds.y + bounds.height + offset - 1,
                bounds.x + bounds.width / 2 + 1, curve_y - 1);
        
        path.quadTo(bounds.x + bounds.width - (bounds.width / 4), curve_y - offset - 2,
                bounds.x + bounds.width - 1, curve_y - 1);
        
        path.lineTo(bounds.x + bounds.width - 1, bounds.y);
        
        graphics.setBackgroundColor(getFillColor());
        
        Pattern gradient = null;
        if(Preferences.STORE.getBoolean(IPreferenceConstants.SHOW_GRADIENT)) {
            gradient = FigureUtils.createGradient(graphics, bounds, getFillColor());
            graphics.setBackgroundPattern(gradient);
        }
        
        graphics.fillPath(path);
        
        if(gradient != null) {
            gradient.dispose();
        }

        // Outline
        graphics.setForegroundColor(getLineColor());
        path.lineTo(bounds.x, bounds.y);
        graphics.drawPath(path);
        path.dispose();
        
        // Line
        graphics.drawLine(bounds.x, bounds.y + TOP_MARGIN, bounds.x + bounds.width, bounds.y + TOP_MARGIN);
        
        graphics.popState();
    }

    @Override
    public Rectangle calculateTextControlBounds() {
        Rectangle bounds = getBounds().getCopy();
        bounds.x += 20;
        bounds.y += TOP_MARGIN + 1;
        bounds.width -= 40;
        bounds.height -= 10;
        return bounds;
    }
}
